package socket

object SocketRAWClient extends App{

  println("Starting Client ...")

  // Simple client USE Hercules App from HW-group.com to test

  import java.io.*
  import java.net.*
  import scala.io.*

  val s = new Socket(InetAddress.getByName("localhost"), 7092)
  lazy val in = new BufferedSource(s.getInputStream()).getLines()
  val out = new PrintStream(s.getOutputStream())

  out.println("Hello, world")
  out.flush()
  println("Received: " + in.next())

  s.close()
}
