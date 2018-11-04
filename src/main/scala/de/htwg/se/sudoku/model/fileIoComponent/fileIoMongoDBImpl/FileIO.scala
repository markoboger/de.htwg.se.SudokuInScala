package de.htwg.se.sudoku.model.fileIoComponent.fileIoMongoDBImpl

import com.google.inject.{Guice, Inject}
import com.google.inject.name.{Named, Names}
import de.htwg.se.sudoku.{MongoDBModule}
import de.htwg.se.sudoku.model.fileIoComponent.FileIOInterface
import de.htwg.se.sudoku.model.gridComponent.GridInterface
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import org.mongodb.scala.model.Projections
import org.mongodb.scala.{Document, MongoClient, MongoDatabase}
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Try

class FileIO @Inject()(@Named("MongoDBHost") host: String, @Named("MongoDBPort") port: Int) extends FileIOInterface {

  private val mongoClient: MongoClient = MongoClient(s"mongodb://$host:$port")
  private val db: MongoDatabase = mongoClient.getDatabase("sudoku-in-scala")
  private val collection = db.getCollection("sudoku-in-scala-col")

  override def load: Try[Option[GridInterface]] = {
    val resultFuture = collection.find().projection(Projections.excludeId()).toFuture()
    val result = Await.result(resultFuture, Duration.Inf)

    var gridOption: Option[GridInterface] = None

    Try {
      val json: JsValue = Json.parse(result.head.toJson())
      val size = (json \ "grid" \ "size").get.toString.toInt
      val injector = Guice.createInjector(new MongoDBModule)

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
        case Some(grid) => {
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
        }
        case None =>
      }
      gridOption
    }
  }

  override def save(grid: GridInterface): Try[Unit] = {
    Try {
      Await.result(collection.drop().toFuture(), Duration.Inf)
      val gameStateDoc = Document.apply(gridToJson(grid).toString())
      val resultFuture = collection.insertOne(gameStateDoc).toFuture()
      Await.result(resultFuture, Duration.Inf)
    }
  }

  def gridToJson(grid: GridInterface) = grid.toJson

  override def unbind(): Unit = {}
}
