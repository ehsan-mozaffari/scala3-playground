package socket

object SocketRAWServer extends App{

  println("Starting Server")

  import java.net._
  import java.io._
  import scala.io._

  val server = new ServerSocket(7092)
  while (true) {
    val s = server.accept()
    val in = new BufferedSource(s.getInputStream()).getLines()
    val out = new PrintStream(s.getOutputStream())

    val inNext = in.next()
    println(s"SERVER RECEIVED: $inNext")
    out.println(s"Echo your message from server: $inNext")
    out.println()
    out.flush()
    s.close()
  }

}
