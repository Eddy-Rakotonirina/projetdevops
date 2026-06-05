# Image Java de base (légère)
FROM eclipse-temurin:8-jdk-alpine

# Dossier de travail dans le conteneur
WORKDIR /app

# Copie le JAR produit par Maven (target/) dans le conteneur
COPY target/monapp-1.0.0.jar app.jar

# Expose le port (si c'est une appli web)
EXPOSE 8080

# Commande lancée au démarrage du conteneur
CMD ["java", "-jar", "app.jar"]