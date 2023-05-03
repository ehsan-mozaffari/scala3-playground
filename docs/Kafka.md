# Kafka

## Kafka Connect
We use kafka to simplify our architecture design and not deal with 
multiple connections and failures. Component of Kafka for connecting and moving
data from/to Kafka.

To use Kafka we have two options:
1. using Kafka producer implemented inside code and be part of code
2. using Kafka connect to connect let say Database to the Kafka broker 
automatically.

Kafka connect source connector: is a publisher to Kafka

Kafka connect Sink connector: is a consumer from Kafka

### How Kafka connect works
Developers implement a framework called `Kafka connect framework` to implement
api for using to connect and publish/subscrible to/from Kafka.

To use Kafka connectors install them in Kafka Connect server and config it.
You can have one Kafka connect cluster and then run workers (source/sink) on it.
You can modify messages in Source or Sink.

### Kafka connect architecture
1. Worker(Has some tasks and connector inside): Fault tolerant and Self managed
You must know how split a task for example reading from 5 tables with max parallel
connections is 1 table per task 
2. Connector: Just for creating and defining task list not copying data.
Has some configuration to make sure each task could run as an independent process
3. Task: Each tasks configured to read data from an assigned list of tables(for example)

The task only responsible to connect to the external system. And handed over the 
data to the worker and worker is responsible to send it to the Kafka cluster.
All the complexity to connect to Kafka is handled by connect framework

## Kafka Schema Registry
A repository based on Kafka to store class json like schema for topics and it has
some `schemaId` that our producer and consumer work with that to validate.

When you want to use the Schema Registry you could add it in Kafka Producer and Consumer config
and add your avro seriallizer dependency and determine where to get avro schemas
to generate classes from `avsc` files.

## Apache Kafka vs Confluent Kafka
They basically are the same but confluence has more none free features like monitoring
and etc.

## Kafkacat
A tools to produce and consume data from a Kafka cluster CLI.

```sh
docker run --net host --rm edenhill/kafkacat:.5.0 \
        -b localhost:9092 \
        -r http://localhost:8081 \
        -s avro \
        -t mysql-debzium-asgard.demo.ORDERS \
        -C -o -10 -q | jq '.'
```
