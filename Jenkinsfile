pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh 'ls $WORKSPACE '
        dir("jenkuber-eg") {
        sh 'echo "Hello World"'
          withMaven {
            sh 'mvn test'
          
          }
        
      }
    }

  }
}
}
