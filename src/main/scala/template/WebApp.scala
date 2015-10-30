package template

import com.typesafe.scalalogging.LazyLogging
import unfiltered.filter.Plan

object WebApp
  extends Plan
  with LazyLogging
  with UploadPlanComponent
  with PetPlanComponent {

  override def intent = PetPlan.intent orElse UploadPlan.intent
  logger.info("Started")
}