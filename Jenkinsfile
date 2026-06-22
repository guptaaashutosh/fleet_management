pipeline {

    agent any

    stages {

        stage('Checkout') {

            steps {
                checkout scm
            }

        }

        stage('Verify Files') {

            steps {

                sh '''
                pwd

                ls -la
                '''
            }

        }

        stage('Make Scripts Executable') {

            steps {

                sh '''
                chmod +x cleanup.sh

                chmod +x deploy.sh
                '''
            }

        }

        stage('Cleanup Previous Deployment') {

            steps {

                sh '''
                ./cleanup.sh
                '''
            }

        }

        stage('Deploy Application') {

            steps {

                sh '''
                ./deploy.sh
                '''
            }

        }

        stage('Verify Kubernetes') {

            steps {

                sh '''
                kubectl get deployments

                kubectl get pods

                kubectl get svc
                '''
            }

        }

    }

    post {

        success {

            echo 'Fleet Management deployed successfully'
        }

        failure {

            echo 'Deployment failed'
        }

    }

}