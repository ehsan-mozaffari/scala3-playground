package typelevel.http4s

import cats.effect.*
import cats.syntax.all.*
import com.comcast.ip4s.*
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.implicits.*
import org.http4s.ember.server.*
object Http4sMain extends IOApp {

  import cats.effect.unsafe.IORuntime
  implicit val runtimeww: IORuntime = cats.effect.unsafe.IORuntime.global

  private val helloWorldService = HttpRoutes
    .of[IO] { case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.")
    }
    .orNotFound

  def run(args: List[String]): IO[ExitCode] =
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(helloWorldService)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)

}
