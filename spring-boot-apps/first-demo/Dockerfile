# Use the official OpenJDK base image
FROM openjdk:11-jre-slim

# Copy the JAR file (created after building the project) into the image
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Set the entry point of the container to run the JAR
ENTRYPOINT ["java","-jar","/app.jar"]
