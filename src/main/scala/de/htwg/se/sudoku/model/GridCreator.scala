package de.htwg.se.sudoku.model

import scala.util.Random

class GridCreator(size:Int) {

  def createRandom(num: Int): Grid = {
    var grid = new Grid(size)
    for {index <- 1 to num} {
      grid = setRandomCell(grid)
    }
    grid
  }

  private def setRandomCell(grid:Grid): Grid = {
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
