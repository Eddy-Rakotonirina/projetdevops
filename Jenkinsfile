pipeline {
  agent any
  tools { maven 'Maven_3.8' }

  stages {

    stage('Git Checkout') {
      steps {
        git credentialsId: 'git_credentials',
            url: 'https://github.com/Eddy-Rakotonirina/projetdevops.git'
      }
    }

    stage('Build') {
      steps { sh 'mvn clean install' }
    }

    stage('Unit Tests') {
      steps { sh 'mvn test' }
    }

    stage('Build Docker Image') {
      steps {
        sh 'docker build -t TON_USER/monapp:1.0.0 .'
      }
    }

    stage('Push to DockerHub') {
      steps {
        withCredentials([string(
          credentialsId: 'dockerhubpass',
          variable: 'dockerHubPass'
        )]) {
          sh 'docker login -u TON_USER -p $dockerHubPass'
          sh 'docker push TON_USER/monapp:1.0.0'
        }
      }
    }
  }

  post {
    failure {
      emailext body: 'Build $BUILD_NUMBER échoué',
               subject: 'Jenkins FAILED',
               to: 'ton@email.com'
    }
  }
}