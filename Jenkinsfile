pipeline {

    agent any

    triggers {
       pollSCM '* * * * *'
    }

    stages {
        stage('Build') {
            steps {
                dir("./"){
                    bat 'mvn install -DskipTests'
                }
            }
        }

    }
}