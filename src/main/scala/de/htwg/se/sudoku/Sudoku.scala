package de.htwg.se.sudoku

import de.htwg.se.sudoku.model.{Grid, Solver, GridCreator, Player}

object Sudoku {
  def main(args: Array[String]): Unit = {
    val grid = new GridCreator(9).createRandom(5)
    val valid = grid.valid
    println("Grid : " + grid.toString)
    println("Grid valid: " + valid)
    val solved:Grid= new Solver(grid).solve._2
    println(solved.toString)
    val valid2 = solved.valid
    println("Grid valid: " + valid)
  }
}
