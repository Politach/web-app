package com.politach

import akka.actor._
import akka.stream.ActorFlowMaterializer
import akka.kernel.Bootable
import com.typesafe.config.ConfigFactory
import com.politach.persist._
import scala.slick.driver.PostgresDriver.api._

class ApiKernel extends Bootable with FlywayInit {
  val config = ConfigFactory.load()
  val serverConfig = config.getConfig("politach")

  implicit val system = ActorSystem("politach", serverConfig)
  implicit val executor = system.dispatcher
  implicit val materializer = ActorFlowMaterializer()

  def startup() = {
    val flyway = initFlyway(config.getConfig("jdbc"))
    flyway.migrate()
    Db.check()

    HttpApi.start(serverConfig)
  }

  def shutdown() = {
    system.shutdown()
  }
}
