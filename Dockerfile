
# Build Stage
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


# Runtime Stage
#Java Runtime Environment (JRE) image based on Java 21.
FROM eclipse-temurin:21-jre
#LABEL adds metadata to the Docker image.
LABEL authors="aashutosh.gupta"

#Creates the /app directory if it does not exist. Sets /app as the default working directory inside the container.
WORKDIR /app

#Copies the generated JAR file from the local target directory into the container. Renames the copied JAR to app.jar.
#Local Machine:
#target/fleet-management-0.0.1-SNAPSHOT.jar
#
#Container:
#/app/app.jar
COPY --from=build /app/target/*.jar app.jar

#Documents that the application listens on port 8082.
EXPOSE 8082

#Defines the command executed when the container starts. Runs the Spring Boot application using Java.
#java -jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]



# End-to-End Flow
#Spring Boot Source Code
#          │
#          ▼
#mvn clean package
#          │
#          ▼
#target/app.jar
#          │
#          ▼
#docker build -t fleet-management .
#          │
#          ▼
#Docker Image
#          │
#          ▼
#docker run -p 8082:8082 fleet-management
#          │
#          ▼
#java -jar app.jar
#          │
#          ▼
#Application Running on Port 8082