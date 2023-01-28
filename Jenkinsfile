
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

    }
}