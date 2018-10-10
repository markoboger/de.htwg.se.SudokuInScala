package de.htwg.se.sudoku.model.gridComponent.gridBaseImpl

import de.htwg.se.sudoku.model.gridComponent.GridInterface

trait GridCreateStrategyTemplate {

  def createNewGrid(size: Int): GridInterface = {
    var grid: GridInterface = new Grid(size)
    grid = prepare(grid)
    grid = fill(grid)
    grid = postProcess(grid)
    grid
  }

  def prepare(grid: GridInterface): GridInterface = {
    // by default do nothing
    grid
  }

  def fill(grid: GridInterface): GridInterface // abstract

  def postProcess(grid: GridInterface): GridInterface = { // default implementation
    grid.markFilledCellsAsGiven
  }

}
