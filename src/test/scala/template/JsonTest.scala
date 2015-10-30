package template

import org.scalatest.{FunSpec, Matchers}

class JsonTest extends FunSpec with Matchers {
  val petJson = """
  |{
  |  "id": 0,
  |  "category": {
  |    "id": 0,
  |    "name": "string"
  |  },
  |  "name": "doggie",
  |  "photoUrls": [
  |  "string"
  |  ],
  |  "tags": [
  |  {
  |    "id": 0,
  |    "name": "string"
  |  }
  |  ],
  |  "status": "available"
  |}
  """.stripMargin

  describe("json") {
    import argonaut._
    import Argonaut._

    it("Skal kunne parse json") {
      petJson.decodeEither[Pet].toEither match {
        case Left(msg)  ⇒ fail(s"couldnt parse $petJson: $msg")
        case Right(pet) ⇒ println(pet)
      }
    }

    it("Should fail correctly") {
      petJson.replaceAll(Available.value, "asd").decodeEither[Pet].toEither match {
        case Left(msg)  ⇒ assert(msg == "asd: [--\\(status)]")
        case Right(pet) ⇒ fail(s"somehow got $pet")
      }
    }
  }
}