pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh 'ls $WORKSPACE '
        dir("project-2-jaab") {
        sh 'echo "Hello Team 3"'
          withMaven {
            sh 'mvn test'
          
          }
        
      }
    }

  }
}
}
