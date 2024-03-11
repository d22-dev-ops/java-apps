
make run-basic

make run-all

mvn clean package
docker build -t helloworld .
docker run -p 8080:8080 helloworld

http://localhost:8080

curl -X GET 'http://localhost:8080/checkCarData?carName=RTX8888'

docker-compose up
docker-compose up --build
docker-compose -f docker-compose.test.yml up -d
docker-compose -f docker-compose.test.yml logs
docker-compose -f docker-compose.test.yml logs main-app