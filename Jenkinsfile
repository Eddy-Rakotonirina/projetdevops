pipeline {
  agent any
  
  tools { 
    // Configuration de l'outil Maven configuré sur ton Jenkins
    maven 'my_maven' 
  }

  environment {
    // Variables pour l'image Docker
    DOCKER_USER = 'eddyrakotonirina'
    IMAGE_NAME = 'jenkins'
    IMAGE_TAG = '1.0.0'
    DOCKER_IMAGE = "${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
  }

  stages {
    // ÉTAPES 1 : Récupération du projet depuis GitHub
    stage('git checkout') {
      steps {
        git credentialsId: 'loginGithub', 
            url: 'https://github.com/Eddy-Rakotonirina/projetdevops.git'
      }
    }

    // ÉTAPE 2 : Compilation ET Packaging (Génère le fichier .jar indispensable pour Docker)
    stage('Build the application') {
      steps { 
        // CORRECTION : On utilise 'package' au lieu de 'compile' pour créer le JAR dans target/
        sh 'mvn clean package -DskipTests' 
      }
    }

    // ÉTAPE 3 : Exécution des tests unitaires
    stage('Unit Test Execution') {
      steps {
        sh 'mvn test'
      }
    }

    // ÉTAPE 4 : Build de l'image Docker
    stage('Build les docker image') {
      steps {
        sh "docker build --tag ${DOCKER_IMAGE} ."
      }
    }

    // ÉTAPE 5 : Mettre l'image dans le dépôt DockerHub
    stage('Push to DockerHub') {
      steps {
        // CORRECTION : Utilisation de usernamePassword pour injecter proprement tes identifiants Docker Hub
        withCredentials([usernamePassword(
          credentialsId: 'loginDockerhub',
          usernameVariable: 'DOCKER_HUB_USER',
          passwordVariable: 'DOCKER_HUB_PASS'
        )]) {
          sh 'echo $DOCKER_HUB_PASS | docker login -u $DOCKER_HUB_USER --password-stdin'
          sh "docker push ${DOCKER_IMAGE}"
        }
      }
    }
  }
}