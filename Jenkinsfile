pipeline {
  agent any
  stages {
    stage('Test') {
        when{
        branch 'kenz'
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
                   withMaven {
                       sh 'mvn clean package -DskipTests'
                   }
               }
           }
}
}
