WorkCount
========
WorkCount Kafka Streams App

> use `./gradlew` instead of `gradle` if you didn't installed `gradle`

### Test
```bash
gradle word-count:test
```
### Build
```bash
gradle word-count:build -x test 
# continuous build with `-t`. 
gradle -t word-count:build
# build docker image
gradle word-count:docker -x test 
```
 
### Run
#### start kafka
```bash
# start kafka 0.11.0.0
docker-compose up
# or start kafka 1.0.0
docker-compose -f docker-compose-k1.yml up
# stop and remove volumes
docker-compose down -v
```

#### start app
```bash
gradle word-count:bootRun
```

### ssh to kafka container
```bash
docker-compose exec kafka bash
# then you can run following commands in this shell
```

### list topics
```bash
kafka-topics --zookeeper zookeeper:2181 --list
```

### to delete topics
```bash
kafka-topics --zookeeper localhost:2181 --delete --topic default-for-ProductCounts-changelog
```

### receive messages
```bash
kafka-console-consumer --bootstrap-server kafka:9092 --topic counts --from-beginning --property print.key=true
```

### send messages
```bash
kafka-console-producer --broker-list kafka:9092 --topic words
```
