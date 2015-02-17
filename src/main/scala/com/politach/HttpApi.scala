package com.politach

import akka.actor._
import akka.stream.FlowMaterializer
import akka.http.Http
import akka.http.server._
import akka.http.server.Directives._
import akka.http.model._
import com.typesafe.config.Config

class HttpApi(config: Config)(implicit system: ActorSystem, materializer: FlowMaterializer) {
  import system.dispatcher

  val interface = config.getString("rest-api.interface")
  val port = config.getInt("rest-api.port")

  val routes: Route =
    pathPrefix("api"/ "v1") {
      path("hello") {
        complete("test")
      }
    }

  def bind() = Http(system).bind(interface = interface, port = port).startHandlingWith(routes)
}

object HttpApi {
  def start(config: Config)
           (implicit system: ActorSystem, materializer: FlowMaterializer): Unit =
    new HttpApi(config).bind()
}
