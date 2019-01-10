package de.htwg.se.sudoku

import com.google.inject.Guice
import de.htwg.se.sudoku.aview.Tui
import de.htwg.se.sudoku.aview.gui.SwingGui
import de.htwg.se.sudoku.controller.controllerComponent.ControllerInterface

import scala.io.StdIn.readLine

object Sudoku {
  val injector = Guice.createInjector(new SudokuModule)
  val controller = injector.getInstance(classOf[ControllerInterface])
  val tui = new Tui(controller)
  //val gui = new SwingGui(controller)
  controller.createNewGrid

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}
