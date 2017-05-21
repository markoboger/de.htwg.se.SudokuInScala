package de.htwg.se.sudoku

import de.htwg.se.sudoku.aview.Tui
import de.htwg.se.sudoku.aview.gui.SwingGui
import de.htwg.se.sudoku.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.sudoku.model.gridComponent.gridAdvancedImpl.Grid

import scala.io.StdIn.readLine

object Sudoku {
  val defaultsize=9
  val controller = new Controller(new Grid(defaultsize))
  val tui = new Tui(controller)
  val gui = new SwingGui(controller)
  controller.createRandomGrid(9,9)

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}
