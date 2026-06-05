FROM eclipse-temurin:21-jre
LABEL authors="aashutosh.gupta"

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","app.jar"]