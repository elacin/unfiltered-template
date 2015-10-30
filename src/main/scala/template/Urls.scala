package template

import linx.Root

object Urls {
  val pets          = Root / "pet" | Root / "arne"
  val pet           = pets / 'petId
  val uploadPicture = pet  / "uploadImage"
}
