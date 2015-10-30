package template

import dispatch._
import linx._

trait DispatchLinxIntegration {
  implicit class LinxReq(r: Req){
    def withLinx[A, X](l: Linx[A, Option[X]])(a: A): Req =
      l.elements(a).head.foldLeft(r)(_ / _)

    def withLinx(l: Linx[Unit, Boolean]): Req =
      l.elements(()).head.foldLeft(r)(_ / _)
  }
}
