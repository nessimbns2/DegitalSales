# Utiliser une image de base avec OpenJDK 17
FROM maven:3.8.3-openjdk-17 AS build


# Installer Maven
RUN apt-get update && apt-get install -y maven

# Définir le répertoire de travail à l'intérieur du conteneur
WORKDIR /app

# Copier le fichier pom.xml et les fichiers source dans le répertoire de travail
COPY . .

# Télécharger les dépendances de Maven et compiler l'application en ignorant les tests
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /app/target/elearning-0.0.1-SNAPSHOT.jar /app/target/elearning-0.0.1-SNAPSHOT.jar

EXPOSE 8080
ENTRYPOINT [ "JAVA", "-jar", "/app/target/elearning-0.0.1-SNAPSHOT.jar" ]
# Démarrer l'application avec l'option JVM pour ouvrir le module java.base
CMD ["java", "--add-opens", "java.base/javax.security.auth=ALL-UNNAMED", "-jar", "/app/target/elearning-0.0.1-SNAPSHOT.jar"]