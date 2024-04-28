pull:
	docker compose -f ./docker/docker-compose.yml pull

build-jar:
	mvn clean install -DskipTests

build-image:
	docker build --no-cache -t ee/rik:1.0.0 .

deploy-postgres:
	docker compose -f ./docker/docker-compose.yml up postgres -d

deploy-rik:
	docker compose -f ./docker/docker-compose.yml up rik -d