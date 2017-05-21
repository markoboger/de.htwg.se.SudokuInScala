package de.htwg.se.sudoku.model.gridComponent.gridMockImpl

import de.htwg.se.sudoku.model.gridComponent.{CellInterface, GridInterface}

class Grid(var size:Int) extends GridInterface{

  size=1

  override def cell(row: Int, col: Int): CellInterface = EmptyCell

  override def set(row: Int, col: Int, value: Int): GridInterface = this

  override def highlight(index: Int): GridInterface = this

  override def setShowCandidates(row: Int, col: Int): GridInterface = this

  override def createNewGrid(size: Int): GridInterface = this

  override def solve: (Boolean, GridInterface) = (true, this)

  override def valid: Boolean = true

  override def solved: Boolean = false

  override def isHighlighted(row: Int, col: Int): Boolean = false

  override def available(row: Int, col: Int): Set[Int] = Set(1)
}

object EmptyCell extends CellInterface {
  override def value: Int = 0

  override def showCandidates: Boolean = false

  override def given: Boolean = false

  override def isSet: Boolean = false
}