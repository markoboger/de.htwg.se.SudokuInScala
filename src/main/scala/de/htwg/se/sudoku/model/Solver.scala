package de.htwg.se.sudoku.model

import scala.util.Random


class Solver(grid:Grid){

  def options = for {
    row <- 0 until grid.size
    col <- 0 until grid.size
  } yield grid.available(row, col)

  def unsolvable:Boolean = options.isEmpty

  def solve: Tuple2[Boolean, Grid] = solve(0)

  def solve(index: Int): Tuple2[Boolean, Grid] = {
    if (grid.solved) return (true, grid) else if (unsolvable) return (false, grid) else {
      val (row, col) = grid.indexToRowCol(index)
      if (grid.cell(row, col).isSet) return solve(index + 1) else {
        val iter = Random.shuffle(grid.available(row, col).toList).iterator
        var res: Tuple2[Boolean, Grid] = (false, grid)
        if (iter.hasNext) {
          for {v <- iter} {
            var g = grid.set(row, col, v)
            res = new Solver(g).solve(index + 1)
            if (res._1 == true) return res
          }
        }
        return res
      }
    }
  }

}
