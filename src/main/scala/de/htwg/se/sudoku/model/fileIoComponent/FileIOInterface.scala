package de.htwg.se.sudoku.model.fileIoComponent

import de.htwg.se.sudoku.model.gridComponent.GridInterface

trait FileIOInterface {

  def load: GridInterface
  def save(grid: GridInterface): Unit

}
