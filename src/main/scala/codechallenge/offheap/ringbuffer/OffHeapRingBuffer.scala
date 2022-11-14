package codechallenge.offheap.ringbuffer

import java.lang.foreign.*
import java.lang.foreign.MemoryLayout.PathElement
import scala.util.Try

trait OffHeapRingBuffer {
  def capacity: Int // total amount of messages
  def messageLength: Int // fixed message length
  def offset(
      msg: MemorySegment
  ): Boolean // return true if capacity is not reached
  def poll(): MemorySegment | Null // return null if buffer is empty
}

class OffHeapRingBufferImp() extends OffHeapRingBuffer {

  var offHeap = new OffHeapRingBufferSegment(10, 20)

  def capacity: Int =
    Try(offHeap.ringBufferArrayLayout.elementCount().toInt).getOrElse(0)

  def messageLength: Int =
    Try(offHeap.ringBufferArrayContentLayout.elementCount().toInt).getOrElse(0)

  def offset(msg: MemorySegment): Boolean = {
    Try(offHeap.varHandleCount.get(msg).toString.toInt)
      .getOrElse(0) >= offHeap.size - 1
  } // return true if capacity is not reached
  def poll(): MemorySegment | Null = {
    if (offHeap.getCount <= 0)
      null
    else
      offHeap.segment
  } // return null if buffer is empty
}
