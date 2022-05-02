@Library('Common-lib') _

def multibranchpipelineJob = new com.ram.multibranchpipelineJob()

def call (){
properties([
    parameters([
        choice(name: 'projectsview', description: 'Please pick one', choices: ['ramkoti', 'BRESI', 'test', 'prod']),
        string(name: 'Name', description: 'RepoName ', defaultValue: 'game-of-life'),
        //string(name: 'Name', description: 'projectsview' , defaultValue: 'test')
        booleanParam(defaultValue: true, description: 'to create multibranch pipeline', name: 'create'),
        booleanParam(description: 'to dellete the job', name: 'deletejob')
    ])
])


pipeline {
    agent any
    stages {
        stage('Create MutiBranch pipline') {
            when { expression {return params.create } }
            steps {
                script {
                    multibranchpipelineJob.createNewJenkinsJob("$params.projectsview", "$params.Name")
                    }
                }
            }
        stage('Delete MutiBranch pipline') {
            when { expression {return params.deletejob } }
            steps {
                script {
                    multibranchpipelineJob.deletejob("$params.projectsview/$params.Name")
                    }
                }
            }

        }
    }
}
