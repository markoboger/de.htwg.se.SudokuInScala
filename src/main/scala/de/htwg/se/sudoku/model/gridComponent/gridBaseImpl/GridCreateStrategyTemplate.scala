package de.htwg.se.sudoku.model.gridComponent.gridBaseImpl

trait GridCreateStrategyTemplate {

  def createNewGrid(size:Int): Grid = {
    var grid = new Grid(size)
    grid = prepare(grid)
    grid = fill(grid)
    grid = postProcess(grid)
    grid
  }

  def prepare(grid: Grid):Grid = {
    // by default do nothing
    grid
  }

  def fill(grid: Grid) : Grid // abstract

  def postProcess(grid: Grid):Grid = { // default implementation
    grid.markFilledCellsAsGiven
  }


}
