klogs
=====
A set of `data microservices` for analysing applogs using `Kafka Streams`

`data microservices` can be deployed and orchestrated using [Spring Cloud Data Flow (SCDF)](https://docs.spring.io/spring-cloud-dataflow/docs/current-SNAPSHOT/reference/htmlsingle/)

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

 ### Deploy
 > Deploying to production.
```bash
nohup java -jar -Dspring.profiles.active=prod word-count-0.1.0-SNAPSHOT.jar > log.log 2>&1 & 
```

#### Build Spring-dataflow image
```bash
 docker-compose build
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

#### start elasticsearch and kibana
```bash
docker-compose up kibana
# test elasticsearch
http://localhost:9200/peg_logs/_search?pretty
```

#### start fluentd and web
```bash
docker-compose up fluentd web
# use `fluentd-*` index in kibana dashboard
```

#### start kafka connect
```bash
docker-compose up connect
```

#### start spring dataflow server
```bash
docker-compose up dataflow
# Open the dashboard at http://localhost:9393/dashboard
# do https://cloud.spring.io/spring-cloud-dataflow/
# bulkload http://bit.ly/Celsius-BUILD-SNAPSHOT-stream-applications-kafka-10-maven
```

#### Stop Infra services (ie., kafka, elasticsearch, kibana)
```bash
# stop and remove volumes
docker-compose down -v
```

#### start app
```bash
gradle word-count:bootRun
# run with `prod` profile.
SPRING_PROFILES_ACTIVE=prod gradle word-count:bootRun
# fource to enable debug logs
SPRING_PROFILES_ACTIVE=prod gradle word-count:bootRun --debug
# via docker
docker-compose up word-count
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

### Gradle Commands
```bash
# upgrade project gradle version
gradle wrapper --gradle-version 4.4.1 --distribution-type all
# gradle daemon status 
gradle --status
gradle --stop
# show dependencies
gradle word-count:dependencies
# refresh dependencies
gradle build -x test --refresh-dependencies 
```
