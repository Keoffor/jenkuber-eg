pipeline {
    environment{
        registry = 'keoffor/kuber-demo'
        dockerHubCreds = 'Docker_hub'
        deploymentFile = 'k8s/api1.yml'
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

//              stage('Deploy to Cluster') {
//                   when {
//                           branch 'main'
//                        }
//                   steps {
//                     dir("project2") {
//                               withKubeConfig([credentialsId: 'jenkinsproject-342600',
//                                 caCertificate: '/etc/ssl/certs/',
//                                  serverUrl: 'https://kubernetes.default',
//                                  contextName: 'gke_jenkinsproject-342600_us-central1_jenkinsproject-342600-gke',
//                                  clusterName: 'jenkinsproject-342600-gke',
//                                  namespace: 'default']) {
//                                 sh("helm upgrade --install jen-depo jenkins-chart --values templates/values.yaml -n default ")
//                               }
//
//                             }
//                           }
//                       }

            stage('Deploy to GKE') {
                               when {
                                   branch 'main'
                               }
                               steps{
                                    echo "build deployment " + deploymentFile
                                    dir("project2") {

                                     sh 'sed -i "s/%TAG%/$BUILD_NUMBER/g" ./k8s/api1.yml'
                                     sh 'cat ./k8s/api1.yml'
                                   step([$class: 'KubernetesEngineBuilder',
                                       projectId: 'jenkinsproject-342600',
                                       clusterName: 'jenkinsproject-342600-gke',
                                       zone: 'us-central1',
                                       manifestPattern: 'k8s/',
                                       credentialsId: 'jenkinsproject-342600',
                                       verifyDeployments: true
                                   ])
                      }
                  }
                }
    }
}
