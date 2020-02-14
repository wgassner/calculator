pipeline {
    environment {
        registry = "wgassner/calculator"
        registryCredential = 'docker'
        dockerImage = ''
    }
    agent any
        /*{
        docker {
            image 'maven:3-alpine'
            args '-v $HOME/.m2:/root/.m2 -v /var/run/docker.sock:/var/run/docker.sock'
        }
    }*/
    triggers {
        pollSCM('* * * * *')
    }
    stages {
        stage('Compile') {
            steps {
                sh "./mvnw compile"
                //sh "mvn -B compile"
            }
        }
        stage('Unit test') {
            steps {
                sh "./mvnw test"
                //sh "mvn -B test"
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
                //sh "mvn -B clean verify"
            }
        }
        stage('Mutation test') {
            steps {
                sh "./mvnw -DtimestampedReports=false org.pitest:pitest-maven:mutationCoverage"
                //sh "mvn -DtimestampedReports=false org.pitest:pitest-maven:mutationCoverage"
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
                sh "./mvnw -DskipTests=true package"
                //sh "mvn -B -DskipTests=true package"
            }
        }
        stage('Docker build') {
            steps {
                script {
                    dockerImage = docker.build registry
                }
            }
        }/*
        stage('Docker push') {
            steps {
                script {
                    docker.withRegistry( '', registryCredential ) {
                        dockerImage.push()
                    }
                }
            }
        }*/
        stage("Deploy to staging") {
            steps {
                script {
                    dockerImage.withRun('--rm -p 8765:8080') {
                        sleep 60
                        sh "./acceptance_test.sh"
                    }
                }
            }
        }
    }
}