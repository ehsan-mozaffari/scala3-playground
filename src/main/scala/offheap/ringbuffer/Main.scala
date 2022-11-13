package offheap.ringbuffer

import offheap.ringbuffer.OffHeapRingBuffer
import offheap.ringbuffer.OffHeapRingBufferImp

import java.lang.foreign.*
import java.lang.foreign.MemoryLayout.PathElement
import scala.util.{Try, Using}

@main def main(): Unit = {

  val runnableOffHeap = new Runnable :
    override def run(): Unit = println(new OffHeapRingBufferImp().capacity)

  new Thread(runnableOffHeap).start()
}
