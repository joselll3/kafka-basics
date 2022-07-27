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
Create new topic with 1 partition and 1 replica
```
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic my_first_topic --partitions 1 --replication-factor 1
```
List topics
```
kafka-topics.sh --bootstrap-server localhost:9092
```
Describe topic
```
kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic my_first_topic
```
List all consumer groups.
```
kafka-consumer-groups.sh  --list --bootstrap-server localhost:9092
```
Describe consumer group.
```
kafka-consumer-groups.sh --describe --group console-consumer-70207 --bootstrap-server localhost:9092
```
Update topic. Add more partitions
```
kafka-topics.sh --bootstrap-server localhost:9092 --alter --topic my_first_topic --partitions 4
```
Delete topic
```
kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic my_first_topic
```

### Execute examples
Run kafka-producer
```
cd kafka-producer
./gradlew bootRun
```
Run kafka-consumer
```
cd kafka-consumer
./gradlew bootRun
```

