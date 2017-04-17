package de.htwg.se.sudoku.model


case class Cell(value: Int) {
  def isSet: Boolean = value != 0
}

