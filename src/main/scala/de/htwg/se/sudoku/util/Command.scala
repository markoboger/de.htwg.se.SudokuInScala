package de.htwg.se.sudoku.util

trait Command {

  def doStep: Unit
  def undoStep: Unit
  def redoStep: Unit

}

