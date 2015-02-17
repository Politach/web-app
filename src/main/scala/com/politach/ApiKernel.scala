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
  val sqlConfig = serverConfig.getConfig("sql")

  val flyway = initFlyway(sqlConfig)
  flyway.migrate()

  implicit val system = ActorSystem("politach", serverConfig)
  implicit val executor = system.dispatcher
  implicit val materializer = ActorFlowMaterializer()
  implicit val db = Database.forConfig("sql", serverConfig)

  def startup() = {
    println("hi")
  }

  def shutdown() = {
    system.shutdown()
  }
}
