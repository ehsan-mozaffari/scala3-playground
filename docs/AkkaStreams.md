# Akka Streams

## Reactive streams 
Specification of async, back pressured streams
* Publisher = emit elements (asynchronously)
* Subscriber = receives elements
* Processor = transform elements along the way
* everything is done **asynchronously**
* Back-pressure

Reactive streams is an SPI (Service Provider Interface), not an API. So, it may have some changes in its _protocol_ or even API and that library which implements the Reactive Streams has the power of choosing protocol or even have an new API. So, `akka-streams` API is what we will talk about.

## Reactive Streams vs Akka Stream terms
* Publisher = Source
  * emit element async
  * may or may not terminate
* Subscriber = Sink
  * terminates only when publisher terminates
  * receive elements
* Processor = Flow
* Direction 
  * upstream = from flow or sink to the source
  * downstream = from source or flow to the sink

## Akka Streams 
Source + flow => new Source
Flow + Sink => new Sink
Graph = Source + flow + Sink