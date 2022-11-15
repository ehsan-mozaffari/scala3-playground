package codechallenge.luckynumbersgame.model

import io.circe.generic.semiauto.*
import io.circe.{Decoder, Encoder}
import sttp.tapir.Schema

case class RandomNumber(randomNumber: String)
object RandomNumber {
  implicit lazy val randomNumberSchema: Schema[RandomNumber] = Schema.derived[RandomNumber]
  implicit lazy val randomNumberEncoder: Encoder[RandomNumber] = deriveEncoder
  implicit lazy val randomNumberDecoder: Decoder[RandomNumber] = deriveDecoder
}
