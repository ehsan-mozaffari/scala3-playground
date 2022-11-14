package codechallenge.offheap.ringbuffer

import codechallenge.offheap.ringbuffer.{OffHeapRingBuffer, OffHeapRingBufferImp}

import java.lang.foreign.*
import java.lang.foreign.MemoryLayout.PathElement
import scala.util.{Try, Using}

object OffHeapRingBufferMain {
  @main def main(): Unit = {

    val runnableOffHeap = new Runnable:
      override def run(): Unit = println(new OffHeapRingBufferImp().capacity)

    new Thread(runnableOffHeap).start()
  }
}
