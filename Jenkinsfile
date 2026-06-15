pipeline{
  agent any

  environment {
    IMAGE_NAME = "demo-app"
    REGISTRY  = "docker.io/aashutoshgupta"
  }

  stages {
      stage("Checkout") {
         steps {
          checkout scm
         }
      }

      stage("Build") {
         steps {
         sh 'mvn clean package -DskipTests'
         }
            }

      stage("Unit Test") {
                steps{
                 sh 'mvn test'
                }
            }

      stage("Docker Build") {
      steps{
                       sh 'docker build -t demo-app .'
                      }
            }

      stage("Push to Registry") {
             steps{
                withCredentials([usernamePassword()])
             }
            }

  }

  post {
     always {
      echo 'executed'
      cleanWs()
     }
  }

}