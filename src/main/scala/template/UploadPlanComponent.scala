package template

import unfiltered.filter.Plan
import unfiltered.filter.request.{MultiPart, MultiPartParams}
import unfiltered.request._
import unfiltered.response._

import scala.util.control.NonFatal
import scala.xml.Elem

trait UploadPlanComponent {
  //a pretty... simple file uploader
  object UploadPlan extends Plan {
    /* constants */
    val metadataName = "additionalMetadata"
    val pictureName  = "picture"

    private def uploadPage(url: String, msg: Either[String, String]): Elem = //party like it's 2005
      <html>
        <body>
          <div>
            {msg.left.toOption.map(e => <div style="color:red;">{e}</div>).toSeq}
            {msg.right.toOption.map(e => <div style="color:green;">{e}</div>).toSeq}
            <form method="POST" action={url} enctype="multipart/form-data">
              <input type="file" name={pictureName}/>
              <input type="text" name={metadataName}/>
              <input type="submit"/>
            </form>
          </div>
        </body>
      </html>

    override val intent: Plan.Intent = {
      case req@GET(Path(url@Urls.uploadPicture(petId))) ⇒
        Ok ~> Html5(uploadPage(url, Right("wanna upload?")))

      case req@POST(MultiPart(Path(url@Urls.uploadPicture(petId)))) ⇒
        try {
          val multipart: MultipartData[Seq[AbstractDiskFile]] =
            MultiPartParams.Memory(req)
          val picture: AbstractDiskFile =
            multipart.files(pictureName).head
          val metadata: String =
            multipart.params(metadataName).head

          //todo: do something useful with picture and metadata

          Ok ~> Html5(uploadPage(url, Right(s"Stored file ${picture.name} with metadata $metadata")))
        } catch {
          case NonFatal(th) ⇒
            BadRequest ~> Html5(uploadPage(url, Left(s"Unable to upload file: ${th.getMessage}")))
        }
    }
  }
}
