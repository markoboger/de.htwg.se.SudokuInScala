package de.htwg.se.sudoku.model.gridComponent.gridBaseImpl

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class SolverSpec extends WordSpec with Matchers {

  "A GridSolver" when {
    "Grid is empty" should {
      val emptyTinyGrid = new Grid(1)
      val emptySmallGrid = new Grid(4)
      val emptyNormalGrid = new Grid(9)
      val emptyGrids = List(emptyTinyGrid, emptySmallGrid, emptyNormalGrid)
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
      val grids = List(randomTinyGrid, randomSmallGrid, randomNormalGrid)
      "solve a Grid without any problems" in {
        for (grid <- grids) {
          grid.valid should be(true)
          val (solvable, solvedGrid) = new Solver(grid).solve
          solvable should be(true)
          solvedGrid.solved should be(true)
        }

      }
    }
  }

}
