Library('common-lib') _
  
def call (){
properties([
    parameters([
        //choice(name: 'projectsview', description: 'Please pick one', choices: ['ramkoti', 'BRESI', 'test', 'prod']),
        string(name: 'Name', description: 'projectsview ', defaultValue: 'Admin'),
        string(name: 'Name', description: 'RepoName ', defaultValue: 'game-of-life'),
        //string(name: 'Name', description: 'projectsview' , defaultValue: 'test')
        booleanParam(defaultValue: true, description: 'to create multibranch pipeline', name: 'create'),
        booleanParam(description: 'to dellete the job', name: 'deletejob'),
        booleanParam(description: 'create folder', name: 'folder')
    ])
])


pipeline {
    agent any
    stages {
        stage('Create MutiBranch pipline') {
            when { expression {return params.create } }
            steps {
                script {
                    multibranchPipelineJob.createNewJenkinsJob("$params.projectsview", "$params.Name")
                    }
                }
            }
          stage('Delete MutiBranch pipline') {
              when { expression {return params.deletejob } }
            steps {
                script {
                    multibranchPipelineJob.deletejob("$params.projectsview/$params.Name")
                    }
                }
            }

          stage('createNewJenkinsFolder') {
               when { expression {return params.folder } }
              steps {
                script {
                    multibranchPipelineJob.createNewJenkinsFolder("$params.projectsview")
                }
            }
        }
      }
    }
}
