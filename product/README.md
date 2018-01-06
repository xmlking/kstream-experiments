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

 ### Deploy
 > Deploying to production.
```bash
nohup java -jar -Dspring.profiles.active=prod product-0.1.0-SNAPSHOT.jar > product.log 2>&1 & 
```

### Run
> run locally
#### start kafka
```bash
docker-compose up kafka
docker-compose -f docker-compose-local.yml up
# stop and remove volumes
docker-compose -f docker-compose-local.yml down -v
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
