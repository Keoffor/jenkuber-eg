pipeline {
    environment{
        registry = 'keoffor/kuber-demo'
        dockerHubCreds = 'Docker_hub'
        dockerImage = ''
    }
  agent any
  stages {
    stage('Test') {
        when{
        branch 'Development'
        }
      steps {
        sh 'ls $WORKSPACE '
        dir("project2") {
        sh 'echo "Hello World"'
          withMaven {
            sh 'mvn test'
          }
         }
        }
       }
          stage('Build') {
               when {
                   branch 'main'
               }
               steps {
                    sh 'ls $WORKSPACE '
                            dir("project2") {
                            sh 'echo "Building"'
                   withMaven {
                       sh 'mvn clean package -DskipTests'
                   }
               }
               }
           }
            stage('Docker Build') {
                   when {
                       branch 'main'
                   }
                   steps {
                        dir("project2") {
                       script {
                           echo "$registry:$currentBuild.number"
                           dockerImage = docker.build "$registry"
                       }
                   }
               }
             }
             stage('Docker Deliver') {
                     when {
                         branch 'main'
                     }
                     steps {
                             dir("project2") {
                         script {
                             docker.withRegistry('', dockerHubCreds) {
                                 dockerImage.push("$currentBuild.number")
                                 dockerImage.push("latest")
                             }
                         }
                     }
                 }
               }

          node{
            stage('Deploy to Cluster') {
                  when {
                          branch 'main'
                       }
                      steps {
                      dir("project2") {
                              withKubeConfig([credentialsId: 'user1', serverUrl: 'https://api.k8s.my-company.com']) {
                                sh("helm upgrade --install jen-depo jenkins-chart --values templates/values.yaml -n default ")
                              }

                            }
                          }
                      }
                  }
    }
}
