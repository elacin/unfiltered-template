package template

import argonaut.Argonaut._
import argonaut._
import unfiltered.response._

/* Kobler sammen Argonaut og Unfiltered, så vi kan svare på en respons med json */
object ResponseJson {
  def apply[A: EncodeJson](a: A, params: PrettyParams = PrettyParams.nospace) =
    JsonContent ~> ResponseString(a.jencode.pretty(params))
}
