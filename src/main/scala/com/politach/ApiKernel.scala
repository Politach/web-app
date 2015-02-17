package com.politach

import akka.actor._
import akka.stream.ActorFlowMaterializer
import akka.kernel.Bootable
import com.typesafe.config.ConfigFactory

class ApiKernel extends Bootable {
  val config = ConfigFactory.load()
  val serverConfig = config.getConfig("politach")

  implicit val system = ActorSystem("politach", serverConfig)
  implicit val executor = system.dispatcher
  implicit val materializer = ActorFlowMaterializer()

  def startup() = {
    println("hi")
  }

  def shutdown() = {
    system.shutdown()
  }
}
