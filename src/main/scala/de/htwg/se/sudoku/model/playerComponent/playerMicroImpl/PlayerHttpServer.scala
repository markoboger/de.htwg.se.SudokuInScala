package de.htwg.se.sudoku.model.playerComponent.playerMicroImpl

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.concurrent.{ExecutionContextExecutor, Future}

class PlayerHttpServer(player: Player) {

  implicit val system: ActorSystem = ActorSystem("system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val route: Route = get {
    pathPrefix("sudoku" / "model" / "player") {
      path("v1" / "string") {
        complete(StatusCodes.OK -> player.name)
      }
    } ~
      post {
        pathPrefix("sudoku" / "model" / "player" / "v1") {
          path(Segment) { name => {
            Player(name)
            complete(StatusCodes.OK)
          }
          }
        }
      }
  }

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(route, "localhost", 8083)

  def unbind(): Unit = {
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}

