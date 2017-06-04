package de.htwg.se.sudoku.controller.controllerComponent.controllerMockImpl

import de.htwg.se.sudoku.controller.controllerComponent.{ControllerInterface, GameStatus}
import de.htwg.se.sudoku.controller.controllerComponent.GameStatus.GameStatus
import de.htwg.se.sudoku.model.gridComponent.{CellInterface, GridInterface}
import de.htwg.se.sudoku.model.gridComponent.gridMockImpl.Grid
import de.htwg.se.sudoku.controller.controllerComponent.GameStatus._

class Controller(var grid: GridInterface) extends ControllerInterface{

  grid = new Grid(1)

  override def gridSize: Int = 1

  override def blockSize: Int = 1

  override def createEmptyGrid(size: Int): Unit = {}

  override def createNewGrid(size: Int): Unit = {}

  override def undo: Unit = {}

  override def redo: Unit = {}

  override def solve: Unit = {}

  override def resize(newSize: Int): Unit = {}

  override def cell(row: Int, col: Int): CellInterface = grid.cell(row, col)

  override def set(row: Int, col: Int, value: Int): Unit = {}

  override def isSet(row: Int, col: Int): Boolean = false

  override def isGiven(row: Int, col: Int): Boolean = false

  override def showCandidates(row: Int, col: Int): Unit = {}

  override def highlight(index: Int): Unit = {}

  override def isHighlighted(row: Int, col: Int): Boolean = false

  override def gridToString: String = grid.toString

  override def isShowCandidates(row: Int, col: Int): Boolean = false

  override def toggleShowAllCandidates: Unit = {}

  override def showAllCandidates: Boolean = false

  override def available(row: Int, col: Int): Set[Int] = Set(1)

  override def gameStatus: GameStatus = IDLE

  override def statusText: String = GameStatus.message(gameStatus)

  override def save: Unit = {}

  override def load: Unit = {}
}
