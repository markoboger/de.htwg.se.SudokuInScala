package de.htwg.se.sudoku.model.gridComponent.gridAdvancedImpl

import com.google.inject.Inject
import com.google.inject.name.Named
import de.htwg.se.sudoku.model.gridComponent.GridInterface
import de.htwg.se.sudoku.model.gridComponent.gridBaseImpl.{GridCreateRandomStrategy, GridCreateStrategyTemplate, Solver, Grid => BaseGrid}

import scala.collection.immutable


class Grid @Inject() ( @Named("DefaultSize") size:Int) extends BaseGrid(size){

  override def createNewGrid:GridInterface = (new GridCreateSymmetricStrategy).createNewGrid(size)

}

class GridCreateSymmetricStrategy extends GridCreateStrategyTemplate{

  override def prepare(grid: GridInterface): GridInterface = (new GridCreateRandomStrategy).createNewGrid(grid.size).solve._2

  override def fill(grid: GridInterface): GridInterface = removeSymmetricPairs(grid, remove=grid.size*grid.size/4)

  def removeSymmetricPairs(_grid:GridInterface, remove:Int ): GridInterface = {
    var grid = _grid
    if (numSetCells(grid) >= 1) {
      for (i <- 1 to remove) {
        val (row1, col1) = randomSetCell(grid)
        val (row2, col2) = symmetricCell(grid.size, row1, col1)
        grid = removePair(grid, row1, col1, row2, col2)
      }
    }
    grid
  }

  def removePair(grid:GridInterface,row1: Int, col1: Int, row2: Int, col2: Int):GridInterface = {
    grid.reset(row1, col1).reset(row2, col2)
  }

  def filledCells(grid:GridInterface):immutable.IndexedSeq[(Int, Int)] = {
    for (row:Int <- 0 until grid.size; col:Int <- 0 until grid.size; if(grid.cell(row, col).isSet))  yield (row, col)
  }

  def numSetCells(grid:GridInterface):Int = filledCells(grid).length

  def randomSetCell(grid:GridInterface):(Int, Int) = filledCells(grid)(scala.util.Random.nextInt(numSetCells(grid)))

  def symmetricCell(size: Int, row:Int, col:Int):(Int, Int) = (size-1 -row, size-1 - col)

}