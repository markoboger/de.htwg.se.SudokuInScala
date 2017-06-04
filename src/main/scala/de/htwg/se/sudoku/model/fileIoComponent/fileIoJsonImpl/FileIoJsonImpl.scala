package de.htwg.se.sudoku.model.fileIoComponent.fileIoJsonImpl

import de.htwg.se.sudoku.model.fileIoComponent.FileIO
import de.htwg.se.sudoku.model.gridComponent.gridBaseImpl.Cell
import de.htwg.se.sudoku.model.gridComponent.{CellInterface, GridInterface}
import play.api.libs.json._

class FileIOJson extends FileIO {

  override def load: GridInterface = ???

  override def save(grid:GridInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("grid.json"))
    pw.write(gridToJson(grid).toString)
    pw.close
  }

  def gridToJson(grid:GridInterface) = {
    Json.obj(
      "grid" -> Json.obj(
        "size" -> JsNumber(grid.size),
        "cells" -> Json.toJson(
          for {row <- 0 until grid.size;
               col <- 0 until grid.size} yield {
            Json.obj(
              "row" -> row,
              "col" -> col,
              "cell" -> Json.toJson (grid.cell(row, col)))
          }
        )
      )
    )
  }

  implicit val cellWrites = new Writes[CellInterface] {
    def writes(cell: CellInterface) = Json.obj(
      "value" -> cell.value,
      "given" -> cell.given,
      "showCandidates" -> cell.showCandidates
    )
  }

}
