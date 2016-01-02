package com.pagero.services.statistics

import akka.actor._
import akka.io.IO
import shapeless.get
import spray.can.Http
import spray.http.HttpHeader
import spray.routing.{HttpService, SimpleRoutingApp}
import spray.util.SprayActorLogging

object Main extends App {
  implicit val system = ActorSystem("my-system")

  val service = system.actorOf(Props[BasicRoute], "spray-sample-service")
//  val service = system.actorOf(Props[SpraySampleActor], "spray-sample-service")
  IO(Http) ! Http.Bind(service, "localhost", 8080)
}


class SpraySampleActor extends Actor with SpraySampleService {
  def actorRefFactory = context

  def receive = runRoute(spraysampleRoute2)
}

trait SpraySampleService extends HttpService {
  val spraysampleRoute1 = {
    path("entity" / Segment) { id =>
      get {
        complete(s"list $id")
      } ~
        post {
          complete("create")
        }
    }
  }
  val spraysampleRoute2 = {
    path("entity") {
      get {
        complete(s"dasdfafd")
      }
    }
  }

}

