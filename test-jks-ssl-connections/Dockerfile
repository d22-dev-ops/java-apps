# Step 1: Build stage using JDK
FROM openjdk:11-jdk-slim AS build

WORKDIR /build
COPY JksTest.java .

# Compile the Java program
RUN javac JksTest.java

# Generate a keystore file with a self-signed certificate
# Note: The keystore and certificate details (passwords, alias, etc.) are examples. Adjust as needed.
RUN keytool -genkeypair -alias mycert -keyalg RSA -keysize 2048 \
    -keystore mykeystore.jks -storepass changeit -validity 365 \
    -dname "CN=example.com, OU=ID, O=Example, L=City, S=State, C=US" -storetype JKS

## Download and import Google's certificate to the keystore
# Use OpenSSL to download Google's certificate
RUN echo | openssl s_client -connect www.google.com:443 -servername www.google.com |\
  sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > google.crt
# Import Google's certificate to the keystore
RUN keytool -import -noprompt -trustcacerts -alias googlecert -file google.crt \
    -keystore mykeystore.jks -storepass changeit

# Step 2: Run stage using JRE
FROM openjdk:11-jre-slim

WORKDIR /app
# Copy the compiled .class file from the build stage
COPY --from=build /build/JksTest.class .
# Copy the generated JKS file from the build stage
COPY --from=build /build/mykeystore.jks .

# Ensure your Java application is configured to use "/app/mykeystore.jks" with the password "changeit"
CMD ["java", "JksTest"]
