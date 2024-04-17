# Define a build-time variable for the keystore name
ARG KEYSTORE_NAME=mykeystore.jks

# Step 1: Build stage using JDK
FROM openjdk:11-jdk-slim AS build

# Redefine the ARG in this stage
ARG KEYSTORE_NAME

WORKDIR /build
COPY . .

# Compile the Java program
RUN javac JksTest.java

# Generate a keystore file with a self-signed certificate
# Note: The keystore and certificate details (passwords, alias, etc.) are examples. Adjust as needed.
RUN keytool -genkeypair -alias mycert -keyalg RSA -keysize 2048 \
    -keystore $KEYSTORE_NAME -storepass changeit -validity 365 \
    -dname "CN=example.com, OU=ID, O=Example, L=City, S=State, C=US" -storetype JKS

# Generate an empty keystore file
RUN keytool -genkeypair -alias mycert -keyalg RSA -keysize 2048 \
    -keystore mykeystore_empty.jks -storepass changeit -validity 365 \
    -dname "CN=example.com, OU=ID, O=Example, L=City, S=State, C=US" -storetype JKS

### repeat this segment for any more certs that need to be added
## Download and import Google's certificate to the keystore
# Use OpenSSL to download Google's certificate
RUN echo | openssl s_client -connect www.google.com:443 -servername www.google.com |\
  sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > google.crt
# Import Google's certificate to the keystore
RUN keytool -import -noprompt -trustcacerts -alias googlecert -file google.crt \
    -keystore $KEYSTORE_NAME -storepass changeit

### repeat this segment for any more certs that need to be added
## Download and import example's certificate to the keystore
# Use OpenSSL to download example's certificate
RUN echo | openssl s_client -connect www.example.com:443 -servername www.example.com |\
  sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > example.crt
# Import example's certificate to the keystore
RUN keytool -import -noprompt -trustcacerts -alias examplecert -file example.crt \
    -keystore $KEYSTORE_NAME -storepass changeit

# Step 2: Run stage using JRE
FROM openjdk:11-jre-slim

# Redefine the ARG in this stage
ARG KEYSTORE_NAME

ENV KEYSTORE_PATH=$KEYSTORE_NAME

WORKDIR /app
# Copy the compiled .class file from the build stage
COPY --from=build /build/JksTest.class .
# Copy the generated JKS file from the build stage
COPY --from=build /build/$KEYSTORE_NAME .
# Copy the generated JKS file from the build stage
COPY --from=build /build/mykeystore_empty.jks .

# Ensure your Java application is configured to use "/app/mykeystore.jks" with the password "changeit"
CMD ["java", "JksTest"]
