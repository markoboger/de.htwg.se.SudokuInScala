package de.htwg.se.sudoku.controller.controllerComponent

import de.htwg.se.sudoku.controller.controllerComponent.GameStatus.GameStatus
import de.htwg.se.sudoku.model.gridComponent.CellInterface

import scala.swing.Publisher

trait ControllerInterface extends Publisher {

  def gridSize: Int

  def blockSize: Int

  def createEmptyGrid: Unit

  def createNewGrid: Unit

  def undo: Unit

  def redo: Unit

  def solve: Unit

  def save: Unit

  def load: Unit

  def resize(newSize: Int): Unit

  def cell(row: Int, col: Int): CellInterface

  def set(row: Int, col: Int, value: Int): Unit

  def isSet(row: Int, col: Int): Boolean

  def isGiven(row: Int, col: Int): Boolean

  def showCandidates(row: Int, col: Int): Unit

  def highlight(index: Int): Unit

  def isHighlighted(row: Int, col: Int): Boolean

  def gridToString: String

  def isShowCandidates(row: Int, col: Int): Boolean

  def toggleShowAllCandidates: Unit

  def showAllCandidates: Boolean

  def available(row: Int, col: Int): Set[Int]

  def gameStatus: GameStatus

  def statusText: String
}

trait ControllerIoInterface  {

  def setGiven(row:Int, col:Int, value:Int)
  def setShowCandidates(row:Int, col:Int)
}


import scala.swing.event.Event

class CellChanged extends Event

case class GridSizeChanged(newSize: Int) extends Event

class CandidatesChanged extends Event
