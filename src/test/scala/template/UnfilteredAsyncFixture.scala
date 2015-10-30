package template

import java.io.File
import java.net.URL

import org.scalatest.{FunSpec, Matchers}
import unfiltered.filter.async
import unfiltered.jetty.Server

trait UnfilteredAsyncFixture extends FunSpec with Matchers {

  private val port = unfiltered.util.Port.any
          val host = dispatch.host("localhost", port)

  def planToTest: async.Plan

  private final def newServer = Server
    .http(port)
    .plan(planToTest)
    .resources(new URL(s"file://${new File( "." ).getCanonicalPath}/web/src/main/webapp"))

  override final protected def withFixture(test: NoArgTest) = {
    val server = newServer
    server.start()
    try {
      test() // Invoke the test function
    } finally {
      server.stop()
      server.destroy()
    }
  }
}