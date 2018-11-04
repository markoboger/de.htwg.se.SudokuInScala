package de.htwg.se.sudoku.model.fileIoComponent.fileIoSlickImpl

import com.google.inject.{Guice, Inject}
import com.google.inject.name.{Named, Names}
import de.htwg.se.sudoku.{SlickModule}
import de.htwg.se.sudoku.model.database.{Cell, Grid}
import de.htwg.se.sudoku.model.fileIoComponent.FileIOInterface
import de.htwg.se.sudoku.model.gridComponent.GridInterface
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Try
import slick.jdbc.H2Profile.api._
import slick.jdbc.meta.MTable

import scala.concurrent.ExecutionContext.Implicits.global

class FileIO @Inject()(@Named("H2Url") url: String, @Named("H2User") dbUser: String) extends FileIOInterface{

  val db =  Database.forURL(url, user = dbUser)

  // create schema if it doesn't exist
  val tables = List(
    TableQuery[Grid],
    TableQuery[Cell]
  )

  val existingTables = db.run(MTable.getTables)
  val createSchemaFuture = existingTables.flatMap( v => {
    val names = v.map(mt => mt.name.name)
    val createIfNotExist = tables.filter( table =>
      (!names.contains(table.baseTableRow.tableName))).map(_.schema.create)
    db.run(DBIO.sequence(createIfNotExist))
  })
  Await.result(createSchemaFuture, Duration.Inf)

  override def load: Try[Option[GridInterface]] = {
    var gridOption: Option[GridInterface] = None

    Try {
      val injector = Guice.createInjector(new SlickModule)

      val grids = TableQuery[Grid]
      val cells = TableQuery[Cell]

      val gridQuery = for (
        grid <- grids
      ) yield grid

      val gridFuture = db.run(gridQuery.result)
      val gridResult = Await.result(gridFuture, Duration.Inf)
      val gridSize = gridResult.head._2

      gridSize match {
        case 1 =>
          gridOption =  Some(injector.instance[GridInterface](Names.named("tiny")))
        case 4 =>
          gridOption =
            Some(injector.instance[GridInterface](Names.named("small")))
        case 9 =>
          gridOption =
            Some(injector.instance[GridInterface](Names.named("normal")))
        case _ =>
      }

      val cellsQuery = for (
        cell <- cells
      ) yield cell

      val cellsFuture = db.run(cellsQuery.result)
      val cellsResult = Await.result(cellsFuture, Duration.Inf)

      gridOption match {
        case Some(grid) => {
          var _grid = grid

          cellsResult.foreach(c => {
            _grid = _grid.set(c._2, c._3, c._4)
            val given = c._5
            val showCandidates = c._6
            if (given) _grid = _grid.setGiven(c._2, c._3, c._4)
            if (showCandidates) _grid = _grid.setShowCandidates(c._2, c._3)
          })

          gridOption = Some(_grid)
        }
        case None =>
      }

      gridOption
    }
  }

  override def save(grid: GridInterface): Try[Unit] = {

    Try{
      val grids = TableQuery[Grid]
      val cells = TableQuery[Cell]

      Await.result(db.run(grids.delete), Duration.Inf)
      Await.result(db.run(cells.delete), Duration.Inf)

      val gridCells = for {
        row <- 0 to grid.size - 1
        col <- 0 to grid.size - 1
      } yield (row, col, grid.cell(row, col))

      val cellSeq = gridCells.zipWithIndex.map{case (c, idx) => {
        cells += (0, c._1, c._2, c._3.value, c._3.given, c._3.showCandidates)
      }}

      db.run(DBIO.seq(
        grids += (0, grid.size),
        DBIO.sequence(cellSeq)
      ))
    }
  }

  override def unbind(): Unit = {}

}
