package codechallenge.luckynumbersgame.server

import codechallenge.luckynumbersgame.endpoint.LuckyNumbersEndpoint

object ServerEndpoints {

  val tapirAll = Seq(
    LuckyNumbersEndpoint.genRandomNumberEndpoint
  )
}
