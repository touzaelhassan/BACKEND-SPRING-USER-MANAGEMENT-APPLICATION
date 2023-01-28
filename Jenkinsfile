
pipeline {

    agent any

    stages {

        stage('Clean & Build') {
            steps {
                dir("./"){
                    bat 'mvn clean package'
                }
            }
        }

        stage('Deploy') {
            steps {
                bat 'docker-compose build'
                bat 'docker-compose up -d'
            }
        }

    }
}