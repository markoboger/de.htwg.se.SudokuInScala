package de.htwg.se.sudoku.model.fileIoComponent.fileIoMicroImpl

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.Source
import scala.util.Try

class FileIoHttpServer extends Directives {
  implicit val system: ActorSystem = ActorSystem("system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  final val FILE_NAME: String = "grid.json"

  val route: Route = get {
    pathPrefix("sudoku" / "model" / "fileio" / "v1") {
      path("load") {

        var source = new String

        Try {
          source = Source.fromFile(FILE_NAME).getLines.mkString
        }

        if (source.nonEmpty) {
          complete(StatusCodes.OK -> source)
        } else {
          complete(StatusCodes.InternalServerError)
        }
      }
    }
  } ~
    post {
      pathPrefix("sudoku" / "model" / "fileio" / "v1") {
        path("save") {
          entity(as[String]) { json =>
            import java.io._

            Try {
              val pw = new PrintWriter(new File(FILE_NAME))
              pw.write(Json.prettyPrint(Json.parse(json)))
              pw.close()
            }

            complete(StatusCodes.OK)
          }
        }
      }
    }

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(route, "localhost", 8089)

  def unbind(): Unit = {
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
