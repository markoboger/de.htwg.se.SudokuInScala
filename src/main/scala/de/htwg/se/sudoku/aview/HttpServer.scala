package de.htwg.se.sudoku.aview

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, StandardRoute}
import akka.stream.ActorMaterializer
import de.htwg.se.sudoku.controller.controllerComponent.ControllerInterface

import scala.concurrent.{ExecutionContextExecutor, Future}

class HttpServer(controller: ControllerInterface) {

  implicit val system: ActorSystem = ActorSystem("RestHttpServerSystem")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val route: Route = get {
    pathSingleSlash {
      complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "<h1>HTWG Sudoku</h1>"))
    }
    pathPrefix("sudoku" / "rest" / "v1") {
      pathEnd {
        gridtoHtml
      } ~
        path("new") {
          controller.createEmptyGrid
          gridtoHtml
        } ~
        path("solve") {
          controller.solve
          gridtoHtml
        } ~
        path("undo") {
          controller.undo
          gridtoHtml
        } ~
        path("redo") {
          controller.redo
          gridtoHtml
        }
    }
  } ~
    post {
      pathPrefix("sudoku" / "rest" / "v1") {
        path(Segment) { command => {
          if (processInputLine(command)) complete(StatusCodes.NoContent) else complete(StatusCodes.BadRequest)
        }
        }
      }
    }

  def gridtoHtml: StandardRoute = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>HTWG Sudoku</h1>" + controller.gridToString))
  }

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(route, "localhost", 8080)

  def unbind(): Unit = {
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

  def processInputLine(input: String): Boolean = {
    input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
      case row :: column :: value :: Nil => controller.set(row, column, value); true
      case _ => false
    }
  }
}
