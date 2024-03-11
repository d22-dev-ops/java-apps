mvn clean package
docker build -t springboot-demo .
docker run -p 8080:8080 springboot-demo
