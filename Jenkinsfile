
pipeline {

    agent any

    stages {

        stage('Build') {
            steps {
                dir("./"){
                    bat 'mvn clean package'
                }
            }
        }

    }
}