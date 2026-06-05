pipeline {
  agent any
  
  tools { 
    // Configuration de l'outil Maven selon l'étape 2 du TP (on utilise 'my_maven' qui est configuré chez toi)
    maven 'my_maven' 
  }

  environment {
    // Variables pour l'image Docker (Adapte avec ton nom d'utilisateur Docker Hub si nécessaire)
    DOCKER_USER = 'eddyrakotonirina'
    IMAGE_NAME = 'jenkins'
    IMAGE_TAG = '1.0.0'
    DOCKER_IMAGE = "${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
  }

  stages {
    // ÉTAPES 1 & 2 : Le "Git Checkout" et la "Compilation" (mvn clean compile)
    // Note : Jenkins fait déjà un checkout automatique au début, mais si ton prof exige le stage visuel :
    stage('git checkout') {
      steps {
        // Utilisation de l'URL HTTPS et des identifiants recommandés par le sujet
        git credentialsId: 'loginGithub', 
            url: 'https://github.com/Eddy-Rakotonirina/projetdevops.git'
      }
    }

    stage('Build the application') {
      steps { 
        // Compilation seule de l'application avec Maven (Étape 2 du TP)
        sh 'mvn clean compile' 
      }
    }

    // ÉTAPE 3 : Exécution des tests unitaires
    stage('Unit Test Execution') {
      steps {
        // Commande demandée à la section 3 du TP
        sh 'mvn test'
      }
    }

    // ÉTAPE 4 : Build de l'image docker
    stage('Build the docker image') {
      steps {
        // Commande de build demandée à la section 4 du TP
        sh "docker build --tag ${DOCKER_IMAGE} ."
      }
    }

    // ÉTAPE 5 : Mettre l'image dans le dépôt DockerHub
    stage('Push to DockerHub') {
      steps {
        // Utilisation de la fonction withCredentials demandée à la section 5 du TP
        withCredentials([string(credentialsId: 'loginDockerhub', variable: 'loginDockerhub')]) {
          sh "docker login -u ${DOCKER_USER} -p \$loginDockerhub"
          sh "docker push ${DOCKER_IMAGE}"
        }
      }
    }
  }
}