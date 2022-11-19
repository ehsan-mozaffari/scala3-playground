package codechallenge.luckynumbersgame.server.logic

import codechallenge.luckynumbersgame.endpoint.LuckyNumbersEndpoint.*

import scala.concurrent.ExecutionContext

class LuckyNumbersServerLogic(implicit ec: ExecutionContext) {

  val genRandomNumberLogic = genRandomNumberEndpoint.serverLogic(a => ???)
}
