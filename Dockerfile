# Utiliser une image de base avec OpenJDK 17
FROM maven:3.8.3-openjdk-17 AS build

# Copier le fichier pom.xml et les fichiers source dans le répertoire de travail
# Set the working directory in the container
WORKDIR /app
COPY pom.xml /app
COPY src /app/src

# Télécharger les dépendances de Maven et compiler l'application en ignorant les tests
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/elearning-0.0.1-SNAPSHOT.jar.original app.jar

EXPOSE 8080
ENTRYPOINT [ "JAVA", "-jar", "/DigitalSales/target/elearning-0.0.1-SNAPSHOT.jar" ]
# Démarrer l'application avec l'option JVM pour ouvrir le module java.base
CMD ["java", "--add-opens", "java.base/javax.security.auth=ALL-UNNAMED", "-jar", "/DigitalSales/target/elearning-0.0.1-SNAPSHOT.jar"]