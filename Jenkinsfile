pipeline {
  agent any
  tools { maven 'Maven_3.8' }

  environment {
    IMAGE_TAG = "1.0.0"
    DOCKER_IMAGE = "eddyrakotonirina/jenkins:${IMAGE_TAG}"
  }

  stages {
    stage('Git Checkout') {
      steps {
        git credentialsId: 'GithubSsh',
            url: 'git@github.com:Eddy-Rakotonirina/projetdevops.git'
      }
    }

    stage('Build & Test') {
      steps { 
        sh 'mvn clean install' 
      }
    }

    stage('Build & Push Docker') {
      agent {
        image 'docker:latest' // Utilise une image contenant le CLI Docker
      }
      steps {
        sh "docker build -t ${DOCKER_IMAGE} ."
        
        withCredentials([usernamePassword(
          credentialsId: 'dockerhubpass',
          usernameVariable: 'DOCKER_USER',
          passwordVariable: 'DOCKER_PASS'
        )]) {
          sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
          sh "docker push ${DOCKER_IMAGE}"
        }
      }
    }
  }
}