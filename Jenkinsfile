pipeline {
    environment {
        registry = "wgassner/calculator"
        registryCredential = ‘dockerhub’
    }
    agent any
    triggers {
        pollSCM('* * * * *')
    }
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
        stage('Mutation test') {
            steps {
                sh "./mvnw -DtimestampedReports=false org.pitest:pitest-maven:mutationCoverage"
                publishHTML (target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/pit-reports',
                    reportFiles: 'index.html',
                    reportName: 'PIT Report'
                ])
            }
        }
        stage('Package') {
            steps {
                sh "./mvnw package"
            }
        }
        stage('Docker build') {
            steps {
                sh "docker build -t wgassner/calculator ."
            }
        }
        stage('Docker push') {
            steps {
                sh "docker push wgassner/calculator"
            }
        }
    }
}