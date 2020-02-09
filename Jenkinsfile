pipeline {
    environment {
        registry = "wgassner/calculator"
        registryCredential = 'docker'
        dockerImage = ''
    }
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
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
                script {
                    dockerImage = docker.build registry
                }
            }
        } /*
        stage('Docker push') {
            steps {
                script {
                    docker.withRegistry( '', registryCredential ) {
                        dockerImage.push()
                    }
                }
            }
        }*/
    }
}