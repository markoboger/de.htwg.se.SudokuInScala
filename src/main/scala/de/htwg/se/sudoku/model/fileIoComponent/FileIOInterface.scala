package de.htwg.se.sudoku.model.fileIoComponent

import de.htwg.se.sudoku.model.gridComponent.GridInterface

trait FileIOInterface {

  def load:Option[GridInterface]
  def save(grid:GridInterface):Unit

}
