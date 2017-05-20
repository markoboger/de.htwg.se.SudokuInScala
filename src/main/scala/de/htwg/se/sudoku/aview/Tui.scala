package de.htwg.se.sudoku.aview

import de.htwg.se.sudoku.controller.{Controller, GameStatus}
import de.htwg.se.sudoku.controller.GameStatus._
import de.htwg.se.sudoku.controller.{CellChanged, GridSizeChanged, CandidatesChanged}

import scala.swing.Reactor

class Tui(controller: Controller) extends Reactor{

  listenTo(controller)
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
      case _ => input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
          case row :: col :: value :: Nil => controller.set(row, col, value)
          case row :: col::Nil => controller.showCandidates(row, col)
          case index::Nil => controller.highlight(index)
          case _ =>
        }

    }
  }

  reactions += {
    case event: GridSizeChanged => printTui
    case event: CellChanged     => printTui
    case event: CandidatesChanged => printCandidates
  }

  def printTui: Unit = {
    println(controller.gridToString)
    println(GameStatus.message(controller.gameStatus))
    controller.gameStatus=IDLE
  }

  def printCandidates: Unit = {
    println("Candidates: ")
    for (row <- 0 until size; col <- 0 until size) {
      if (controller.isShowCandidates(row, col)) println("("+row+","+col+"):"+controller.available(row, col).toList.sorted)
    }
  }
}
