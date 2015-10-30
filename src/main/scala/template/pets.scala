package template

case class Category(id: CategoryId, name: Name)
case class Tag(id: TagId, name: Name)
case class Pet(id: PetId, category: Category, name: Name, photoUrls: Seq[String], tags: Seq[Tag], status: Status)

sealed abstract class Status(val value: String)
case object Available extends Status("available")
case object Pending extends Status("pending")
case object Sold extends Status("sold")

object Status {
  /* poor man's enum */
  val values = List[Status](Available, Pending, Sold)
  def find(s: String): Option[Status] = values.find(_.value == s)
}

/* for type-safety we wrap these */
case class Name(value: String) extends AnyVal
case class PetId(value: Long) extends AnyVal
case class TagId(value: Long) extends AnyVal
case class CategoryId(value: Long) extends AnyVal


object Pet {
  import argonaut._, Argonaut._
  /* we can define a codec by defining an (Encoder, Decoder) pair */
  implicit val StatusEncoder   = EncodeJson[Status](s ⇒ jString(s.value))
  implicit val StatusDecoder   = DecodeJson[Status](c ⇒ c.as[String].flatMap {
    value ⇒ Status.find(value).fold[DecodeResult[Status]](DecodeResult.fail(value, c.history))(DecodeResult.ok)
    }
  )
  implicit val CategoryIdCodec  = CodecJson[CategoryId](id ⇒ jNumber(id.value), _.as[Long].map(CategoryId))
  implicit val TagIdCodec       = CodecJson[TagId](id ⇒ jNumber(id.value), _.as[Long].map(TagId))
  implicit val PetIdCodec       = CodecJson[PetId](id ⇒ jNumber(id.value), _.as[Long].map(PetId))
  implicit val NameCodec        = CodecJson[Name](n ⇒ jString(n.value), _.as[String].map(Name))

  implicit val CategoryCodec    = casecodec2(Category.apply, Category.unapply)("id", "name")
  implicit val TagCodec         = casecodec2(Tag.apply,      Tag.unapply)("id", "name")
  implicit val PetCodec         = casecodec6(Pet.apply,      Pet.unapply)("id", "category", "name", "photoUrls", "tags", "status")
}