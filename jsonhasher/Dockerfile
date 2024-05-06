# Stage 1: Build
FROM maven:3.8.4-openjdk-11-slim AS build
WORKDIR /app

# Copy only the POM file to fetch dependencies
COPY pom.xml .
# Fetch dependencies
RUN mvn dependency:go-offline

# Copy your source code and build the application
COPY src /app/src
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM openjdk:11-jre-slim
WORKDIR /app

# Copy only the built artifact and nothing else
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app/app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
