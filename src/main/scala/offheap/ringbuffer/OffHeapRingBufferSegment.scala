package offheap.ringbuffer

import java.lang.foreign.*
import java.lang.foreign.MemoryLayout.PathElement
import scala.collection.mutable
import scala.util.{Try, Using}

class OffHeapRingBufferSegment(
    val size: Int,
    val strLen: Int
) extends Thread {

  var segment: MemorySegment = null

  var close = false

  final val ringBufferArrayContentLayout = MemoryLayout
    .sequenceLayout(
      strLen,
      MemoryLayout.structLayout(ValueLayout.JAVA_CHAR.withName("ch"))
    )
    .withName("content")

  final val ringBufferArrayLayout =
    MemoryLayout
      .sequenceLayout(
        size,
        MemoryLayout.structLayout(
          ValueLayout.JAVA_INT.withName("index"),
          ringBufferArrayContentLayout
        )
      )
      .withName("array")

  final val ringBufferMemoryLayout: GroupLayout =
    MemoryLayout.structLayout(
      ValueLayout.JAVA_INT.withName("head"),
      ValueLayout.JAVA_INT.withName("tail"),
      ValueLayout.JAVA_INT.withName("count"),
      ringBufferArrayLayout
    )



  final val varHandleHead =
    ringBufferMemoryLayout.varHandle(PathElement.groupElement("head"))
  final val varHandleTail =
    ringBufferMemoryLayout.varHandle(PathElement.groupElement("tail"))
  final val varHandleCount =
    ringBufferMemoryLayout.varHandle(PathElement.groupElement("count"))
  final val varHandleArrayIndex = ringBufferMemoryLayout.varHandle(
    PathElement.groupElement("array"),
    PathElement.sequenceElement(),
    PathElement.groupElement("index")
  )
  final val varHandleArrayContent = ringBufferMemoryLayout.varHandle(
    PathElement.groupElement("array"),
    PathElement.sequenceElement(),
    PathElement.groupElement("content"),
    PathElement.sequenceElement(),
    PathElement.groupElement("ch")
  )

  final val varHandleArray = ringBufferMemoryLayout.varHandle(
    PathElement.groupElement("array"),
  )

  def closeSession(): Unit = this.close = true



  def getHead:Int = Try(varHandleHead.get(segment).toString.toInt).getOrElse(0)
  def getTail: Int = Try(varHandleTail.get(segment).toString.toInt).getOrElse(0)
  def getCount:Int = Try(varHandleCount.get(segment).toString.toInt).getOrElse(0)
  def getArraySegment : Option[MemorySegment] = Try(varHandleArray.get(segment).asInstanceOf[MemorySegment]).toOption
  def getArrayIndex(index: Int):Int = Try(varHandleArrayIndex.get(segment, index).toString.toInt).getOrElse(0)
  def getArrayContent(index: Int): Option[String] =
    (0 until strLen)
      .map(strIndex =>
        Try(
          varHandleArrayContent.get(segment, index, strIndex).toString
        ).toOption
      )
      .filter(_.isDefined)
      .map(_.get)
      .reduceOption(_ + _)


  override def run(): Unit = {

    Using(MemorySession.openConfined()) { session =>

      /*val allocator: SegmentAllocator = SegmentAllocator.newNativeArena(session)
      val segment: MemorySegment = allocator.allocate(ringBufferMemoryLayout)
      val segment: MemorySegment = MemorySegment.allocateNative(ptsLayout, MemorySession.openImplicit())
      val segmentOfCharsString = allocator.allocateArray(ValueLayout.JAVA_CHAR, Array.ofDim[Char](5): _*) */
      segment = MemorySegment.allocateNative(ringBufferMemoryLayout, session)

      def loop(): Unit = if close then () else loop()
      loop()

    }.recover { case e: Throwable =>
      e.printStackTrace()
    }

  }
}
