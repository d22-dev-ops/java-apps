# Use an official Maven image with a JDK
FROM maven:3.8.4-openjdk-17-slim AS build

# Copy local code to the container image
WORKDIR /app
COPY src ./src
COPY pom.xml ./

# Build a release artifact.
RUN mvn package -DskipTests

# Use OpenJDK for running the application
FROM openjdk:17-slim
COPY --from=build /app/target/sns-sqs-app-1.0-SNAPSHOT.jar /sns-sqs-app.jar

# Run the application
CMD ["java", "-jar", "/sns-sqs-app.jar"]
