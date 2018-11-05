package de.htwg.se.sudoku.aview

import de.htwg.se.sudoku.controller.Controller
import de.htwg.se.sudoku.model.{Grid, GridCreator, Solver}
import de.htwg.se.sudoku.util.Observer

class Tui(controller: Controller) extends Observer{

  controller.add(this)
  val size = 9
  val randomCells:Int = size*size/8

  def processInputLine(input: String):Unit = {
    input match {
      case "q" =>
      case "n"=> controller.createEmptyGrid(size)
      case "r" => controller.createRandomGrid(size, randomCells)
      case "z" => controller.undo
      case "y" => controller.redo
      case "s" => controller.solve
      case _ => input.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
          case row :: column :: value :: Nil => controller.set(row, column, value)
          case _ =>
        }

    }
  }

  override def update: Boolean = {
    println(controller.gridToString)
    println(GameStatus.message(controller.gameStatus))
    controller.gameStatus=IDLE
    true
  }
}
