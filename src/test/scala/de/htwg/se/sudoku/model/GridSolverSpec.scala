package de.htwg.se.sudoku.model

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
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
      val randomTinyGrid = new GridCreator(1).createRandom(0)
      val randomSmallGrid = new GridCreator(4).createRandom(4)
      val randomNormalGrid = new GridCreator(9).createRandom(9)
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
    "Grid is almost full" should {
      val randomSmallGrid = new GridCreator(4).createRandom(20)
      val randomNormalGrid = new GridCreator(9).createRandom(100)
      val grids = List(randomSmallGrid,randomNormalGrid)
      "if the Grid is full with random numbers, it is likely not solvable, the Solver should then return false" in {
        for (grid <- grids) {
          grid.valid should be(true)
          val (solvable, solvedGrid) = new Solver(grid).solve
          solvable should be(false)
          solvedGrid.solved should be(false)
        }

      }
    }
  }


}
