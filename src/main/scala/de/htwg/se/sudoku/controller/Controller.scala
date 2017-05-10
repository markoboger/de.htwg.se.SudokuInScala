package de.htwg.se.sudoku.controller

import de.htwg.se.sudoku.controller.GameStatus._
import de.htwg.se.sudoku.model.{Grid, GridCreateRandomStrategy}
import de.htwg.se.sudoku.util.{Observable, UndoManager}

class Controller(var grid: Grid) extends Observable {

  var gameStatus: GameStatus = IDLE
  private val undoManager = new UndoManager

  def createEmptyGrid(size: Int): Unit = {
    grid = new Grid(size)
    notifyObservers
  }

  def createRandomGrid(size: Int, randomCells: Int): Unit = {
    grid = (new GridCreateRandomStrategy).createNewGrid(size)
    notifyObservers
  }

  def gridToString: String = grid.toString

  def gridToHtml: String = grid.toHtml

  def set(row: Int, col: Int, value: Int): Unit = {
    undoManager.doStep(new SetCommand(row, col, value, this))
    notifyObservers
  }

  def solve: Unit = {
    undoManager.doStep(new SolveCommand(this))
    notifyObservers
  }

  def undo: Unit = {
    undoManager.undoStep
    notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep
    notifyObservers
  }
}
