package de.htwg.se.sudoku.model.playerComponent.playerMicroImpl

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer
import de.htwg.se.sudoku.model.playerComponent.PlayerInterface

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContextExecutor, Future}

case class Player(name: String) extends PlayerInterface {

  implicit val system: ActorSystem = ActorSystem("system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  override def toString: String = {
    lazy val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://localhost:8081/sudoku/model/player/v1/string"))
    Await.result(responseFuture, 5000 millis).asInstanceOf[String]
  }
}
