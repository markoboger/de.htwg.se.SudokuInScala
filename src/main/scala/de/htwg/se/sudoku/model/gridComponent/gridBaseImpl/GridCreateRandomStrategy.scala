package de.htwg.se.sudoku.model.gridComponent.gridBaseImpl

import de.htwg.se.sudoku.model.gridComponent.GridInterface

import scala.util.Random

class GridCreateRandomStrategy extends GridCreateStrategyTemplate {

  def fill(_grid: GridInterface): GridInterface = {
    val num = Math.sqrt(_grid.size).toInt
    var grid: GridInterface = new Grid(_grid.size)
    for { index <- 1 to num } {
      grid = setRandomCell(grid)
    }
    grid
  }

  private def setRandomCell(grid: GridInterface): GridInterface = {
    val row = Random.nextInt(grid.size)
    val column = Random.nextInt(grid.size)
    val availableValueSet = grid.available(row, column).toIndexedSeq
    val numAvailableValues = availableValueSet.size
    if (numAvailableValues > 0) {
      val value = availableValueSet(Random.nextInt(numAvailableValues))
      grid.set(row, column, value)
    } else grid
  }
}
