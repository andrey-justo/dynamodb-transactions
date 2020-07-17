default: build

docker_project = dynamodb-transactions

docker_compose_dev = docker-compose \
			 -p $(docker_project) \
			 -f $(shell pwd)/docker-compose-local.yml

build:
	./gradlew clean build

start-devenv:
	$(docker_compose_dev) up -d --build --no-recreate
	sleep 2s # Giving time to all instances are up and running
	./local-env/populate/dynamodb.sh

clear-devenv:
	$(docker_compose_dev) down -v --remove-orphans
