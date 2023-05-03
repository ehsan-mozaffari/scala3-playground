# Fun Technologies check list

- [ ] Load testing 
  - [ ] Gatling: Read the [docs](https://gatling.io/docs/gatling/reference/current/general/concepts/) carefully. It is fun with lots of plugins
    - [ ] Complex [scenarios](https://gist.github.com/sadikovi/a428c6a75d585086db1a)
    - [ ] Ways to [write](https://medium.com/@SamPFacer/a-different-way-of-writing-gatling-scenarios-5d45168b6199) different scenarios
    - [ ] Complete [guide](https://octoperf.com/blog/2020/03/05/kraken-gatling-getting-started-with-simulation-scripts/#html-resources-inferring) of gatling implementation 
    - [ ] Add Gatling on sbt commands (For example `sbt testAll`) for load testing in application using the main models of application. For instance, using account model to run Gatling tests. If the model changed you have to come to think of the change you have to in the `gatling` module or `performance-test` module. [sbt new command](https://www.scala-sbt.org/1.x/docs/Commands.html) and even upload latest performance test result using `backstage`.

  - [ ] Kraken: Load testing IDE based on Gatling with multi-hosts load injection using a Kubernetes cluster.  

- [ ] Kubernetes
  - [ ] Read my company Jira page of Kubernetes


- [ ] Kafka
  - [ ] Confluence eco-system
  - [ ] Kafka and [KSql](https://www.confluent.io/blog/best-kafka-tools-that-boost-developer-productivity/)
  - [ ] Schema registry
  - [ ] Tools to work with Kafka and publish a message with defined schema registry. Like **Kafka tool**, **Kafka-ui**, **Kafka manager**, **Kafdrop** etc.
  - [ ] Reading Book of Kafka streams
  - [ ] Avro Schema and publish data using [command](https://kafka-tutorials.confluent.io/kafka-console-consumer-producer/kafka.html) or kafkatool. [Create Avro message](https://www.learningjournal.guru/article/kafka/how-to-create-avro-messages-using-kafka-producer/)
  - [ ] 