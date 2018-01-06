Raw-Word-Count
==============
Raw Word-Count Kafka Streams App

> use `./gradlew` instead of `gradle` if you didn't installed `gradle`

### Test
```bash
gradle raw-word-count:test
```
### Build
```bash
gradle raw-word-count:build -x test 
# continuous build with `-t`. 
gradle -t raw-word-count:build
# build docker image
gradle raw-word-count:docker -x test 
```
 
 ### Deploy
 > Deploying to production.  
 ```bash
 nohup java -jar -Dspring.profiles.active=prod raw-word-count-0.1.0-SNAPSHOT.jar > raw-word-count.log 2>&1 & 
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
gradle raw-word-count:bootRun
# run with `prod` profile.
SPRING_PROFILES_ACTIVE=prod gradle raw-word-count:bootRun
# fource to enable debug logs
SPRING_PROFILES_ACTIVE=prod gradle raw-word-count:bootRun --debug
```

### ssh to kafka container
```bash
docker-compose exec kafka bash
# then you can run following commands in this shell
```

### receive messages
```bash
kafka-console-consumer --bootstrap-server kafka:9092 --from-beginning --property print.key=true --topic counts 
```

### send messages
```bash
kafka-console-producer --broker-list kafka:9092 --topic words
```
