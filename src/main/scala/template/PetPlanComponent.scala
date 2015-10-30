package template

import com.typesafe.scalalogging.LazyLogging
import unfiltered.filter.Plan
import unfiltered.request._
import unfiltered.response._

trait PetPlanComponent extends PetStoreComponent {
  object PetPlan extends Plan with LazyLogging {
    override val intent: Plan.Intent = {
      case req@GET(Path(Urls.pets())) ⇒
        Ok ~> ResponseJson(PetStore.filtered(_ ⇒ true))

      case req@GET(Path(Urls.pet(petIdStr: String)))  & Params(p)⇒
        val pid  = PetId(petIdStr.toLong)
        println(p.get("query parameter"))
        Ok ~> ResponseJson(PetStore.read(pid))
    }
  }
}