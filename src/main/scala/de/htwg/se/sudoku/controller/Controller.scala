package de.htwg.se.sudoku.controller

import de.htwg.se.sudoku.model.{Grid, Solver, GridCreator}
import de.htwg.se.sudoku.util.{Observable, Command, UndoManager}

class Controller(var grid:Grid) extends Observable{

  private val undoManager = new UndoManager

  def createEmptyGrid(size: Int):Unit = {
    grid = new Grid(size)
    notifyObservers
  }

  def createRandomGrid(size: Int, randomCells:Int):Unit = {
    grid = new GridCreator(size).createRandom(randomCells)
    notifyObservers
  }

  def gridToString: String = grid.toString

  def set(row: Int, col: Int, value: Int):Unit = {
    undoManager.doStep(new SetCommand(row, col, value))
    notifyObservers
  }

  def solve: Boolean = {

    val (success, g) = new Solver(grid).solve
    grid = g
    notifyObservers
    success
  }

  def undo: Unit = {
    undoManager.undoStep
    notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep
    notifyObservers
  }

  class SetCommand(row:Int, col: Int, value:Int) extends Command {
    override def doStep: Unit =   grid = grid.set(row, col, value)

    override def undoStep: Unit = grid = grid.set(row, col, 0)

    override def redoStep: Unit = grid = grid.set(row, col, value)
  }

}
