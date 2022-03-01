pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh 'ls $WORKSPACE '
        dir("project2") {
        sh 'echo "Hello Team3"'
          withMaven {
            sh 'mvn test'
          
          }
        
      }
    }

  }
}
}
