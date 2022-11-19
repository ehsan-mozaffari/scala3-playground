package codechallenge.luckynumbersgame.endpoint

import codechallenge.luckynumbersgame.model.RandomNumber
import sttp.tapir.*
import sttp.tapir.json.circe.*

object LuckyNumbersEndpoint {

  val genRandomNumberEndpoint =
    endpoint
      .get
      .in("random-number")
      .out(statusCode)
      .out(jsonBody[RandomNumber])
}
