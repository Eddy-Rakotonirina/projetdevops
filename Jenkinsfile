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
        // Attention : tu utilises une URL HTTPS, mais un credential SSH. 
        // Assure-toi que c'est le bon type. Si c'est SSH, utilise : git@github.com:Eddy-Rakotonirina/projetdevops.git
        git credentialsId: 'GithubSsh',
            url: 'https://github.com/Eddy-Rakotonirina/projetdevops.git'
      }
    }

    stage('Build & Test') {
      steps { 
        // mvn clean install fait déjà les tests, pas besoin de deux étapes distinctes
        sh 'mvn clean install' 
      }
    }

    stage('Build Docker Image') {
      steps {
        // CORRECTION : Suppression du point en trop à la fin de la commande
        sh "docker build -t ${DOCKER_IMAGE} ."
      }
    }

    stage('Push to DockerHub') {
      steps {
        // Utilisation de 'usernamePassword' au lieu de 'string' pour une meilleure sécurité
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

  post {
    failure {
      emailext body: "Build ${env.BUILD_NUMBER} échoué",
               subject: "Jenkins FAILED - ${env.JOB_NAME}",
               to: 'ton@email.com'
    }
  }
}