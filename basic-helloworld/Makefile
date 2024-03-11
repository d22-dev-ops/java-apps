# Vars
DOCKER_REGISTRY=delerium227
IMAGE_NAME=$(shell basename "$$(pwd)")
#GIT_HASH := $(shell git rev-parse --short HEAD)
GIT_HASH := githash
BUILD_ID := $(shell date +%Y%m%d%H%M%S)
CONTAINER_NAME := $(IMAGE_NAME)-$(BUILD_ID)

# Tasks
run-basic: clean build test run

run-full: clean lint-dockerfile build test scan push run

lint-dockerfile:
	docker run --rm -i hadolint/hadolint hadolint -t error -f gnu - < ./Dockerfile

build:
	docker build -t $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID) .
	docker tag $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID) $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID)
	docker tag $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID) $(DOCKER_REGISTRY)/$(IMAGE_NAME):latest

test:
#	docker run --rm --name $(CONTAINER_NAME) --rm $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID) bash -c './tests/version_checks.sh'
	docker run --name $(CONTAINER_NAME) --entrypoint "/bin/sleep" -d $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID) 1000
	docker exec -i $(CONTAINER_NAME) bash < tests/container-tests/version-checks.sh
	docker rm -f $(CONTAINER_NAME)

test-stubbed: clean
	docker-compose -f docker-compose.test.yml up --build --abort-on-container-exit

scan:
#	trivy image $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID) --security-checks vuln --offline-scan
#	trivy image $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID) --security-checks vuln --no-progress --offline-scan

push:
	docker login -u $(DOCKER_HUB_USERNAME) -p $(DOCKER_HUB_TOKEN)
	docker push $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID)
	docker push $(DOCKER_REGISTRY)/$(IMAGE_NAME):latest

run:
	docker run --name $(IMAGE_NAME)-$(BUILD_ID) -d -p 8080:8080 $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID)
	@echo "Container $(IMAGE_NAME)-$(BUILD_ID) has been started"
	@echo "To log into the container run, use the following command:"
	@echo "docker exec -it $(IMAGE_NAME)-$(BUILD_ID) /bin/bash"
	docker ps -a
	docker logs $(IMAGE_NAME)-$(BUILD_ID)

inspect: clean build
	docker inspect $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID)
	docker run --name $(IMAGE_NAME)-$(BUILD_ID) --entrypoint "/bin/sleep" -d -p 8080:8080 $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID) 1000
	@echo "Container $(IMAGE_NAME)-$(BUILD_ID) has been safe started and will stay alive for 10 minutes"
	@echo "To log into the container run, use the following command:"
	@echo "docker exec -it $(IMAGE_NAME)-$(BUILD_ID) /bin/bash"
	docker ps -a

clean:
	@docker ps -a -q --filter "name=$(IMAGE_NAME)" | xargs -r docker rm -f


clean-images:
	@docker images --format "{{.Repository}}:{{.Tag}} {{.CreatedAt}}" | grep "$(IMAGE_NAME)" | sort -r | awk 'NR>4 {print $$1}' | xargs -r docker rmi -f
