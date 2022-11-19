package codechallenge.luckynumbersgame.webserver

import codechallenge.luckynumbersgame.server.ServerEndpoints
import sttp.tapir.ztapir.*
import sttp.tapir.server.ziohttp.{ZioHttpInterpreter, ZioHttpServerOptions}
import sttp.tapir.server.interceptor.log.DefaultServerLog
import sttp.tapir.server.ziohttp.{ZioHttpInterpreter, ZioHttpServerOptions}
import zhttp.http.HttpApp
import zhttp.service.server.ServerChannelFactory
import zhttp.service.{EventLoopGroup, Server}
import zio.{Console, Scope, Task, ZIO, ZIOAppArgs, ZIOAppDefault}
import com.typesafe.scalalogging.*

class ZIOHttpServer extends LazyLogging {

  private val serverOptions: ZioHttpServerOptions[Any] =
    ZioHttpServerOptions.customiseInterceptors
      .serverLog(
        DefaultServerLog[Task](
          doLogWhenReceived = msg => ZIO.succeed(logger.debug(msg)),
          doLogWhenHandled = (msg, error) => ZIO.succeed(error.fold(logger.debug(msg))(err => logger.debug(msg, err))),
          doLogAllDecodeFailures = (msg, error) => ZIO.succeed(error.fold(logger.debug(msg))(err => logger.debug(msg, err))),
          doLogExceptions = (msg: String, ex: Throwable) => ZIO.succeed(logger.debug(msg, ex)),
          noLog = ZIO.unit
        )
      )
      .options

//  val app: HttpApp[Any, Throwable] = ZioHttpInterpreter(serverOptions).toHttp(ServerEndpoints.tapirAll)
}
