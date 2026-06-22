pipeline {

  agent any

  stages {

    stage('Checkout') {

      steps {

        echo 'Checking out code...'

        checkout scm

      }

    }

    stage('Verify Files') {

      steps {

        sh 'pwd'

        sh 'ls -la'

      }

    }

  }

}