package com.pagero.services.statistics

import spray.http.HttpHeaders.RawHeader
import spray.http.MediaTypes._
import spray.http._
import spray.json.DefaultJsonProtocol
import DefaultJsonProtocol._ // !!! IMPORTANT, else `convertTo` and `toJson` won't work correctly
import spray.routing.{HttpServiceActor, RequestContext}
import spray.json._

object JsonImplicits extends DefaultJsonProtocol {
  implicit val impPerson = jsonFormat2(Person)
}

class BasicRoute extends HttpServiceActor with DefaultJsonProtocol{
  //  def receive = runRoute(route1)
  //  def receive = runRoute(myRoute1)
  //  def receive = runRoute(pipedRoute1)
  //  def receive = runRoute(routeTransform)
  //  def receive = runRoute(routeFilter)
//  def receive = runRoute(directiveRoute)
  def receive = runRoute(directiveJson)

  val route1 = {
    path("ping") {
      get {
        complete("PONG")
      }
    }
  }

  /**
    * Bare minimum route.
    */
  val myRoute1 = (r: RequestContext) => {
    sender ! HttpResponse(entity = "MyRoute1")
  }

  /**
    * Bare minimum route.
    */
  val myRoute2 = complete {
    "This is same as myRoute1, much cleaner"
  }

  val routePipe1 = (r: RequestContext) => {
    println("pipe1")
    r.reject()
  }
  val routePipe2 = (r: RequestContext) => {
    println("pipe2")
    r.complete("Pipe2")
  }

  val pipedRoute1 = routePipe1 ~ routePipe2
  val routeTransform = {
    path("") {
      respondWithHeader(RawHeader("XXXX", "yyyyyy")) {
        complete {
          "done"
        }
      }
    }
  }

  val routeFilter = {
    path("") {
      headerValue(h => if (h.name == "xx") Some(h.value) else None) { value =>
        complete(s"xxx = $value") //this hits only if we have http req. header xx in the req.
      }
    } ~ path("") {
      complete {
        "Fallback route when you dont have request header xx"
      }
    }
  }

  val directiveRoute = {
    path("university" / IntNumber) { x =>
      complete(s"university --> $x")
    }
  }


  val directiveJson = {
    path("person" / IntNumber) { x =>
      respondWithMediaType(`application/json`) {
        complete {
          val emp = new Person("sherlock holmns", x)
          HttpEntity(emp.toJson.prettyPrint)
        }
      }
    }
  }
}

case class Person(name: String, age: Int)