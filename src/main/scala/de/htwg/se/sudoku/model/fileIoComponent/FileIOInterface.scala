package de.htwg.se.sudoku.model.fileIoComponent

import de.htwg.se.sudoku.model.gridComponent.GridInterface

import scala.util.Try

trait FileIOInterface {

  def load: Try[Option[GridInterface]]
  def save(grid: GridInterface): Try[Unit]

}
