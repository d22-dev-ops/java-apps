# Vars
DOCKER_REGISTRY=delerium227
IMAGE_NAME=$(shell basename "$$(pwd)")
#GIT_HASH := $(shell git rev-parse --short HEAD)
GIT_HASH := githash
BUILD_ID := $(shell date +%Y%m%d%H%M%S)
CONTAINER_NAME := $(IMAGE_NAME)-$(BUILD_ID)

# Tasks

# Task "run-basic": Clean up, build, test, and then run the basic set of operations.
run-basic: clean build test run

# Task "run-full": Perform a full cycle of operations including clean up, linting Dockerfiles, building, testing, scanning for vulnerabilities, pushing the image to a registry, and finally running the container.
run-full: clean lint-dockerfile build test scan push run

# Task "lint-dockerfile": Lint the Dockerfile to identify and show errors based on best practices and coding standards.
lint-dockerfile:
	docker run --rm -i hadolint/hadolint hadolint -t error -f gnu - < ./Dockerfile

# Task "build": Build the Docker image from the Dockerfile and tag it with both a unique build identifier and as the latest version.
build:
	docker build -t $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID) .
	docker tag $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID) $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID)
	docker tag $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID) $(DOCKER_REGISTRY)/$(IMAGE_NAME):latest

# Task "test": Run tests against the built image to ensure it meets all expected conditions and requirements.
test:
#	docker run --rm --name $(CONTAINER_NAME) --rm $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID) bash -c './tests/version_checks.sh'
	docker run --name $(CONTAINER_NAME) --entrypoint "/bin/sleep" -d $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID) 1000
	docker exec -i $(CONTAINER_NAME) bash < tests/container-tests/version-checks.sh
	docker rm -f $(CONTAINER_NAME)

# Task "test-stubbed": Run stubbed tests using Docker Compose to orchestrate and manage the test environment.
test-stubbed: clean
	docker-compose -f docker-compose.test.yml up --build --abort-on-container-exit

# Task "scan": Scan the Docker image for vulnerabilities. (Note: Actual scanning commands are commented out in the provided script.)
scan:
#	trivy image $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID) --security-checks vuln --offline-scan
#	trivy image $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID) --security-checks vuln --no-progress --offline-scan

# Task "push": Push the built Docker image to a Docker registry using credentials provided.
push:
	docker login -u $(DOCKER_HUB_USERNAME) -p $(DOCKER_HUB_TOKEN)
	docker push $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID)
	docker push $(DOCKER_REGISTRY)/$(IMAGE_NAME):latest

# Task "run": Run the Docker container from the image, making it accessible on a specified port.
run:
	docker run --name $(IMAGE_NAME)-$(BUILD_ID) -d -p 8080:8080 $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID)
	@echo "Container $(IMAGE_NAME)-$(BUILD_ID) has been started"
	@echo "To log into the container run, use the following command:"
	@echo "docker exec -it $(IMAGE_NAME)-$(BUILD_ID) /bin/bash"
	docker ps -a
	docker logs $(IMAGE_NAME)-$(BUILD_ID)

# Task "inspect": Clean up any previous instances, build the image, and run a container for inspection purposes.
inspect: clean build
	docker inspect $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID)
	docker run --name $(IMAGE_NAME)-$(BUILD_ID) --entrypoint "/bin/sleep" -d -p 8080:8080 $(DOCKER_REGISTRY)/$(IMAGE_NAME):$(GIT_HASH)-$(BUILD_ID) 1000
	@echo "Container $(IMAGE_NAME)-$(BUILD_ID) has been safe started and will stay alive for 10 minutes"
	@echo "To log into the container run, use the following command:"
	@echo "docker exec -it $(IMAGE_NAME)-$(BUILD_ID) /bin/bash"
	docker ps -a

# Task "clean": Remove any containers that match the IMAGE_NAME pattern, ensuring the environment is clean for the next operations.
clean:
	@docker ps -a -q --filter "name=$(IMAGE_NAME)" | xargs -r docker rm -f

# Task "clean-images": Remove all Docker images matching the IMAGE_NAME pattern, except for the 4 most recently created ones, to manage image storage effectively.
clean-images:
	@docker images --format "{{.Repository}}:{{.Tag}} {{.CreatedAt}}" | grep "$(IMAGE_NAME)" | sort -r | awk 'NR>4 {print $$1}' | xargs -r docker rmi -f
