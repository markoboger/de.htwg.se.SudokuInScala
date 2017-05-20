package de.htwg.se.sudoku

import de.htwg.se.sudoku.model.{Grid, GridCreateRandomStrategy, Player, Solver}
import de.htwg.se.sudoku.controller.{CellChanged, Controller}
import de.htwg.se.sudoku.aview.Tui
import de.htwg.se.sudoku.aview.gui.SwingGui

import scala.io.StdIn.readLine

object Sudoku {
  val controller = new Controller(new Grid(9))
  val tui = new Tui(controller)
  val gui = new SwingGui(controller)
  controller.publish(new CellChanged)

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}
