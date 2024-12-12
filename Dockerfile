# Use Maven to build the application
FROM maven:3.8.3-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml /app
COPY src /app/src

# Download Maven dependencies and compile the application, skipping tests
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the executable jar file from the build stage to the container
COPY --from=build /app/target/elearning-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the jar file with JVM options
CMD ["java", "--add-opens", "java.base/javax.security.auth=ALL-UNNAMED", "-jar", "app.jar"]