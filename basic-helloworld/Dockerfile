# Use a Maven image for building the project
FROM maven:3.8.1-openjdk-11-slim as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use OpenJDK for running the application
FROM openjdk:11-jre-slim
COPY --from=build /app/target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=default
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
