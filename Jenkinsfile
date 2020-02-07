pipeline {
    agent any
    stages {
        stage('Compile') {
            steps {
                sh "./mvnw compile"
            }
        }
        stage('Unit test') {
            steps {
                sh "./mvnw test"
            }
        }
        stage('Code coverage') {
            steps {
                publishHTML (target: [
                    reportDir: 'target/jacoco-report',
                    reportFiles: 'index.html',
                    reportName: 'JaCoCo Report'
                ])
                sh "./mvnw clean verify"
            }
        }
    }
}