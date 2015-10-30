package template

object Runner extends App {
  unfiltered.jetty.Server.http(8080).plan(WebApp).run()
}
