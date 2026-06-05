pipeline {
  agent any
  tools { maven 'Maven_3.8' }

  environment {
    // Utilisation d'une variable pour le tag facilite la maintenance
    IMAGE_TAG = "1.0.0"
    DOCKER_IMAGE = "eddyrakotonirina/jenkins:${IMAGE_TAG}"
  }

  stages {
    stage('Git Checkout') {
      steps {
        // Correction de l'URL pour correspondre aux credentials SSH
        git credentialsId: 'GithubSsh',
            url: 'git@github.com:Eddy-Rakotonirina/projetdevops.git'
      }
    }

    stage('Build & Test') {
      steps { 
        // mvn clean install fait déjà les tests
        sh 'mvn clean install' 
      }
    }

    stage('Build Docker Image') {
      steps {
        sh "docker build -t ${DOCKER_IMAGE} ."
      }
    }

    stage('Push to DockerHub') {
      steps {
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