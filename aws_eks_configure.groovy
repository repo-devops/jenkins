def call ()

pipeline {
    agent { label 'buildtool' }
    parameters {
        choice choices: ['dev', 'int', 'stg', 'prod'], description: 'Select environment to configure aws cli  ', name: 'Env'
        string defaultValue: 'tooling', description: 'enter the cluster name', name: 'cluster_name', trim: true
        choice choices: ['us-east-1', 'us-east-2'], description: 'Select Region ', name: 'Region'
    }
    stages{
        stage{'aws_configure and k8s configure'}{
            when {
                expression { params.Env == 'dev' }
            }
            withCredentials([usernamePassword(credentialsId: 'aws_config_dev', passwordVariable: 'access_key', usernameVariable: 'access_id')]) {
            sh '''
            aws configure --profile myprofile set output format json
            export AWS_ACCESS_KEY_ID="${access_id}"
            export AWS_SECRET_ACCESS_KEY="${access_key}"
            # export AWS_SESSION_TOKEN="${parms.token}"
            aws configure --profile myprofile set region "${params.Region}"
            aws sts get-caller-identity '''
         }
        }
         stage('Cluster configure'){
            when { anyOf {
                    expression { params.Env == 'dev' }
                    expression { params.Env == 'int' }
                    expression { params.Env == 'stg' }
                    expression { params.Env == 'prod'}
                } }
            sh '''
            echo "get k8s cluster .kube" 
            aws eks update-kubeconfig --region "${params.Region}" --name "${params.cluster_name}"
            echo "successful k8s configured "
            '''
         }
    }
}
