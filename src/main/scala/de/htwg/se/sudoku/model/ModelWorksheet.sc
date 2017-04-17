package de.htwg.se.sudoku.model

object ModelWorksheet {

  val grid1:Grid = new Grid(4)

  grid1.cell(0, 0).isSet
  val grid2:Grid = grid1.set(0, 0, 1)
  grid2.cell(0, 0).isSet
  grid2.row(0)
  grid2.col(0)
  grid2.block(0)
}