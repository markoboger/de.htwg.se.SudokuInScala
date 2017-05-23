package de.htwg.se.sudoku.aview

import akka.actor.ActorSystem
import akka.http.javadsl.server.directives.RouteDirectives
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ ContentTypes, HttpEntity }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ Route, StandardRoute }
import akka.stream.ActorMaterializer
import de.htwg.se.sudoku.controller.Controller

class HttpServer(controller: Controller) {
  val size = 9
  val randomCells: Int = size * size / 8

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val route: Route = get {
    pathSingleSlash {
      complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "<h1>HTWG Sudoku</h1>"))
    }
    path("sudoku") {
      gridtoHtml
    } ~
      path("sudoku" / "new") {
        controller.createEmptyGrid(size)
        gridtoHtml
      } ~
      path("sudoku" / "random") {
        controller.createRandomGrid(size, randomCells)
        gridtoHtml
      } ~
      path("sudoku" / "solve") {
        controller.solve
        gridtoHtml
      } ~
      path("sudoku" / "undo") {
        controller.undo
        gridtoHtml
      } ~
      path("sudoku" / "redo") {
        controller.redo
        gridtoHtml
      } ~
      path("sudoku" / Segment) { command =>
        {
          processInputLine(command)
          gridtoHtml
        }
      }
  }

  def gridtoHtml: StandardRoute = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>HTWG Sudoku</h1>" + controller.gridToHtml))
  }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  def unbind = {
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

  def processInputLine(input: String): Unit = {
    input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
      case row :: column :: value :: Nil => controller.set(row, column, value)
      case _ =>
    }
  }

}
