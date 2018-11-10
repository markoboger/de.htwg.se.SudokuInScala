package de.htwg.se.sudoku.model

import org.scalatest.{Matchers, WordSpec}

class GridSolverSpec extends WordSpec with Matchers {

  "A GridSolver" when {
    "Grid is empty" should {
      val emptyTinyGrid = new Grid(1)
      val emptySmallGrid = new Grid(4)
      val emptyNormalGrid = new Grid(9)
      val emptyGrids = List(emptyTinyGrid,emptySmallGrid,emptyNormalGrid)
      "solve a Grid without any problems" in {
        for (emptyGrid <- emptyGrids) {
          val (solvable, solvedGrid) = new Solver(emptyGrid).solve
          solvable should be(true)
          solvedGrid.solved should be(true)
        }
      }
    }
    "Grid is not empty" should {
      val randomTinyGrid = (new GridCreateRandomStrategy).createNewGrid(1)
      val randomSmallGrid = (new GridCreateRandomStrategy).createNewGrid(4)
      val randomNormalGrid = (new GridCreateRandomStrategy).createNewGrid(9)
      val grids = List(randomTinyGrid,randomSmallGrid,randomNormalGrid)
      "solve a Grid without any problems" in {
        for (grid <- grids) {
          grid.valid should be(true)
          val (solvable, solvedGrid) = new Solver(grid).solve
          solvable should be(true)
          solvedGrid.solved should be(true)
        }
      }
    }
    "Grid is not solvable"  should {
      val unsolvableGrid =new Grid(4).set(0,0,1).set(1,1,1)
      "find out that it is unsolvable" in {
        val (solvable, solvedGrid) = new Solver(unsolvableGrid).solve
        solvable should be(false)
        solvedGrid.solved should be(false)
      }

    }
  }
}
