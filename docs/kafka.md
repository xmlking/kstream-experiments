Kafka
=====
Kafka, ZooKeeper, Kafka-Manager, Kafka Connector, Kafka Streams, Kafka Schema Registry 

### Install
> install kafka CLI tools locally.
```bash
cd /Developer/Applications/
curl http://packages.confluent.io/archive/4.0/confluent-oss-4.0.0-2.11.zip | tar xz
```

### Kafka Manager
http://localhost:9000/clusters/OmniKafkaCluster/topics

### List Topics
```bash
/Developer/Applications/confluent-4.0.0/bin/kafka-topics  --zookeeper localhost --list
```

### Delete Topics
```bash
/Developer/Applications/confluent-4.0.0/bin/kafka-topics --zookeeper localhost --delete --topic words
```

### Receive Messages
```bash
/Developer/Applications/confluent-4.0.0/bin/kafka-console-consumer \
--bootstrap-server localhost:9092 --from-beginning --property print.key=true --topic counts
```

### Send Messages
```bash
/Developer/Applications/confluent-4.0.0/bin/kafka-console-producer \
--broker-list localhost:9092 --topic words
```

### Purge Kafka Topic
```bash
# Temporarily update the retention time on the topic to one second:
/Developer/Applications/confluent-4.0.0/bin/kafka-configs --zookeeper localhost \
--entity-type topics --alter --add-config retention.ms=1000 --entity-name words
 
# wait until topic is pruned (this command should not show any messages)
/Developer/Applications/confluent-4.0.0/bin/kafka-console-consumer \
--bootstrap-server localhost:9092 --from-beginning --property print.key=true --topic words

# Then set it back to default
/Developer/Applications/confluent-4.0.0/bin/kafka-configs --zookeeper localhost \
--entity-type topics --alter --delete-config retention.ms --entity-name words
# Check if it is back to default
/Developer/Applications/confluent-4.0.0/bin/kafka-configs --zookeeper localhost \
--entity-type topics --describe --entity-name words
```

### Reset Offsets
```bash
# list consumer-groups
/Developer/Applications/confluent-4.0.0/bin/kafka-consumer-groups \
--bootstrap-server localhost:9092 --list
/Developer/Applications/confluent-4.0.0/bin/kafka-consumer-groups \
--bootstrap-server localhost:9092 --list | grep connector

# see offsets for `work-count` consumer-group or for e.g., `connect-peg-logs-connector`
/Developer/Applications/confluent-4.0.0/bin/kafka-consumer-groups \
--bootstrap-server localhost:9092 --describe --group work-count

# make sure the `work-count` app is stoped, then run to reset offsets:
/Developer/Applications/confluent-4.0.0/bin/kafka-consumer-groups \
--bootstrap-server localhost:9092  --group work-count \
--reset-offsets --to-earliest --all-topics --execute
```
### Connector

REST API [Reference](https://docs.confluent.io/current/connect/restapi.html)

> List the connector plugins available on this worker
```bash
curl localhost:8083/connector-plugins
```
> Listing active connectors on a worker 
```bash
curl http://localhost:8083/connectors
```
> Getting connector configuration
```bash
curl http://localhost:8083/connectors/peg-logs-connector
```
> Getting tasks for a connector
```bash
curl http://localhost:8083/connectors/peg-logs-connector/tasks
```
> Getting connector status
```bash
curl http://localhost:8083/connectors/peg-logs-connector/status
```
> Restarting a connector
```bash
curl -XPOST 'localhost:8083/connectors/peg-logs-connector/restart'
```
> Restarting a task
```bash
curl -XPOST 'localhost:8083/connectors/peg-logs-connector/tasks/0/restart'
```
> Pausing a connector (useful if downtime is needed for the system the connector interacts with)
```bash
curl -XPUT 'localhost:8083/connectors/peg-logs-connector/pause'
```
> Resuming a connector
```bash
curl -XPUT 'localhost:8083/connectors/peg-logs-connector/resume'
```
> Deleting a connector
```bash
curl -XDELETE 'localhost:8083/connectors/peg-logs-connector'
```

> Create Connector for Docker
```bash
# create elasticsearch connector
# Peg Logs
curl -XPOST -H 'Content-type:application/json' 'localhost:8083/connectors' -d '{
 "name": "peg-logs-connector",
 "config" : {
  "key.converter": "org.apache.kafka.connect.storage.StringConverter",
  "key.converter.schemas.enable": "false",
  "value.converter.schemas.enable": "false",
  "connector.class" : "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
  "tasks.max" : "1",
  "topics" : "peg_logs", 
  "connection.url" : "http://elasticsearch:9200",
  "type.name" : "peg",
  "key.ignore" : "false",
  "schema.ignore" : "true"
 }
}'

# Call Logs
curl -XPOST -H 'Content-type:application/json' 'localhost:8083/connectors' -d '{
 "name": "call-logs-connector",
 "config" : {
  "key.converter": "org.apache.kafka.connect.storage.StringConverter",
  "key.converter.schemas.enable": "false",
  "value.converter.schemas.enable": "false",
  "connector.class" : "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
  "tasks.max" : "1",
  "topics" : "call_logs", 
  "connection.url" : "http://elasticsearch:9200",
  "type.name" : "call",
  "key.ignore" : "false",
  "schema.ignore" : "true"
 }
}'

# Error Logs
curl -XPOST -H 'Content-type:application/json' 'localhost:8083/connectors' -d '{
 "name": "error-logs-connector",
 "config" : {
  "key.converter": "org.apache.kafka.connect.storage.StringConverter",
  "key.converter.schemas.enable": "false",
  "value.converter.schemas.enable": "false",
  "connector.class" : "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
  "tasks.max" : "1",
  "topics" : "error_logs", 
  "connection.url" : "http://elasticsearch:9200",
  "type.name" : "error",
  "key.ignore" : "false",
  "schema.ignore" : "true"
 }
}'
```

### Schema Registry 
* http://localhost:8081/
