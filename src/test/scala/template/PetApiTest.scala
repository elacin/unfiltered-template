package template

import argonaut.Argonaut._
import argonaut._
import dispatch._
import unfiltered.filter.Plan

import scala.concurrent.ExecutionContext.Implicits.global

class PetApiTest extends UnfilteredSyncFixture with DispatchLinxIntegration {
  object planToTest extends Plan with PetPlanComponent{
    override val intent = PetPlan.intent
  }

  val samplePet = Pet(
    PetId(0),
    Category(CategoryId(0), Name("category name")),
    Name("name"),
    Seq.empty,
    Seq.empty,
    Available
  )

  planToTest.PetStore.create(samplePet)

  describe("PetApiTest") {
    it("Should return all pets") {

      //bygg opp request
      val req: Req = host.withLinx(Urls.pets)

      //kjør requesten. > lar deg mappe om svaret direkte før du har en future
      val futureRes: Future[String] =
        Http(req OK as.String)

      //dispatch gir deg () syntax for å vente på future
      val res: String = futureRes()

      res.decodeOption[List[Pet]] should be (Some(List(samplePet)))
    }

    it("Should return a specific pet") {
      val req: Req =
        host.withLinx(Urls.pet)(samplePet.id.value.toString)

      val futureRes: Future[String] =
        Http(req OK as.String)

      val res: String = futureRes()
      res.decodeOption[Pet] should be (Some(samplePet))
    }
  }
}