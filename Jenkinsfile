pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh 'ls $WORKSPACE '
        dir("project-2-jaab") {
        sh 'echo "Hello Teams"'
          withMaven {
            sh 'mvn test'
          
          }
        
      }
    }

  }
}
}
