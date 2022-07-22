# kafka-basics
First steps to install kafka and sample producer and consumer microservice

### Install and start kafka
Create containers
```
cd Docker
docker-compose up --d
```
See all running containers
```
docker-compose ps
```
Stop all containers
```
docker-compose stop
```
Remove all containers
```
docker-compose down
```

### Basic kafka commands
Run bash commands in kafka container
```
docker exec -it kafka bash
```