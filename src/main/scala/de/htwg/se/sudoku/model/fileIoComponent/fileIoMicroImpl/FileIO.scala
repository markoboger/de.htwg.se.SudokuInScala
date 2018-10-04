package de.htwg.se.sudoku.model.fileIoComponent.fileIoMicroImpl

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.POST
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import com.google.inject.Guice
import com.google.inject.name.Names
import de.htwg.se.sudoku.MicroSudokuModule
import de.htwg.se.sudoku.model.fileIoComponent.FileIOInterface
import de.htwg.se.sudoku.model.gridComponent.GridInterface
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import play.api.libs.json.Json

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContextExecutor, Future}
import scala.util.{Success, Try}

class FileIO extends FileIOInterface {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  override def load: Try[Option[GridInterface]] = {
    val responseFuture = Http()
      .singleRequest(HttpRequest(uri = "http://localhost:8089/sudoku/model/fileio/v1/load"))

    val result = Await.result(responseFuture, 5000 millis)

    if (result.status.isSuccess) {
      Success(jsonGridToGridOption(Unmarshal(result.entity).to[String]))
    } else {
      throw new IllegalStateException("Loading the game failed, reason: " + result.status.reason())
    }
  }

  override def save(grid: GridInterface): Try[Unit] = {
    Try {
      val httpRequest = HttpRequest(POST,
        uri = "http://localhost:8089/sudoku/model/fileio/v1/save",
        entity = grid.toJson.toString())
      val responseFuture: Future[HttpResponse] = Http().singleRequest(httpRequest)
      val result = Await.result(responseFuture, 5000 millis)

      if (!result.status.isSuccess()) {
        throw new IllegalStateException("Saving the game failed, reason: " + result.status.reason())
      }
    }
  }


  def jsonGridToGridOption(jsonString: Future[String]): Option[GridInterface] = {

    var gridOption: Option[GridInterface] = None

    jsonString.map(string => {
      val json = Json.parse(string)

      Try {
        val size = (json \ "grid" \ "size").get.toString.toInt
        val injector: ScalaInjector = Guice.createInjector(new MicroSudokuModule)

        size match {
          case 1 =>
            gridOption =
              Some(injector.instance[GridInterface](Names.named("tiny")))
          case 4 =>
            gridOption =
              Some(injector.instance[GridInterface](Names.named("small")))
          case 9 =>
            gridOption =
              Some(injector.instance[GridInterface](Names.named("normal")))
          case _ =>
        }
        gridOption match {
          case Some(grid) =>
            var _grid = grid
            for (index <- 0 until size * size) {
              val row = (json \\ "row") (index).as[Int]
              val col = (json \\ "col") (index).as[Int]
              val cell = (json \\ "cell") (index)
              val value = (cell \ "value").as[Int]
              _grid = _grid.set(row, col, value)
              val given = (cell \ "given").as[Boolean]
              val showCandidates = (cell \ "showCandidates").as[Boolean]
              if (given) _grid = _grid.setGiven(row, col, value)
              if (showCandidates) _grid = _grid.setShowCandidates(row, col)
            }
            gridOption = Some(_grid)
          case None =>
        }
        gridOption
      }
    })

    gridOption
  }
}
