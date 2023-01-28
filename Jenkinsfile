
pipeline {

    agent any

    triggers {
       pollSCM 'H * * * *'
    }

    stages {
        stage('Build') {
            steps {
                dir("./"){
                    bat 'mvn clean package -DskipTests'
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