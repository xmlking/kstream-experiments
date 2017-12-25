klogs
=====
Analyzer Kafka Streams App

> use `./gradlew` instead of `gradle` if you didn't installed `gradle`

### Test
```bash
gradle analyzer:test
```
### Build
```bash
gradle analyzer:build -x test 
# continuous build with `-t`. 
gradle -t analyzer:build
# build docker image
gradle analyzer:docker -x test 
```
 
### Run
#### start kafka
```bash
# start kafka 1.0.0
docker-compose up
# or start kafka 0.10.1.1
docker-compose -f docker-compose-10.yml up
# stop and remove volumes
docker-compose -f docker-compose-10.yml down -v
```

#### start app
```bash
gradle analyzer:bootRun
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

### Gradle Commands
```bash
# upgrade project gradle version
gradle wrapper --gradle-version 4.4.1 --distribution-type all
# gradle daemon status 
gradle --status
gradle --stop
# show dependencies
gradle analyzer:dependencies
# refresh dependencies
gradle build -x test --refresh-dependencies 
```
