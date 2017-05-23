package de.htwg.se.sudoku.model

case class Cell(value: Int, given: Boolean = false) {
  def isSet: Boolean = value != 0

  override def toString: String = value.toString.replace('0', ' ') + (if (given) "." else " ")
}

