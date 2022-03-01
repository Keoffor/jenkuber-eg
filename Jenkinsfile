pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh 'ls $WORKSPACE '
        dir("projet2") {
        sh 'echo "Hello World best"'
          withMaven {
            sh 'mvn test'
          
          }
        
      }
    }

  }
}
}
