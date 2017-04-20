package de.htwg.se.sudoku

import de.htwg.se.sudoku.model.{Grid, GridCreator, Player, Solver}
import de.htwg.se.sudoku.aview.Tui

import scala.io.StdIn.readLine

object Sudoku {
  var grid = new Grid(9)
  val tui = new Tui

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      println("Grid : " + grid.toString)
      input = readLine()
      grid = tui.processInputLine(input, grid)
    } while (input != "q")
  }
}
