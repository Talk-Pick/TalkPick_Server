FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
COPY ./build/libs/*SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]