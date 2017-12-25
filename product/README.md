Product
========
Product Kafka Streams App

> use `./gradlew` instead of `gradle` if you didn't installed `gradle`

### Test
```bash
gradle product:test
```
### Build
```bash
gradle product:build -x test 
# continuous build with `-t`. 
gradle -t product:build
# build docker image
gradle product:docker -x test 
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
gradle product:bootRun
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
kafka-console-consumer --bootstrap-server kafka:9092 \
--property key.deserializer=org.apache.kafka.common.serialization.IntegerDeserializer --property print.key=true --topic product-counts
```

### send messages
```bash
kafka-console-producer --broker-list kafka:9092 --topic products
 ```
 
> Enter the following in the console producer (one line at a time) and watch the output on the console consumer:
```json 
{"id":"123"}
{"id":"124"}
{"id":"125"}
{"id":"123"}
{"id":"123"}
{"id":"123"}
 ```
