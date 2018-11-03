package de.htwg.se.sudoku.model.gridComponent.gridAdvancedImpl

import org.scalatest.{Matchers, WordSpec}

class GridCreateSymmetricStrategySpec extends WordSpec with Matchers  {

  "An  GridCreateSymmetricStrategy" should {
    val createStrategy = new GridCreateSymmetricStrategy
    val preparedTinyGrid = createStrategy.prepare(new Grid(size = 1))
    val preparedSmallGrid = createStrategy.prepare(new Grid(size = 4))
    "have a solved Grid as prepare step" in {
      val preparedTinyGrid = createStrategy.prepare(new Grid(size = 1))
      preparedTinyGrid.solved should be(true)
      preparedSmallGrid.solved should be(true)
    }
    "should correctly count the set cells" in {
      createStrategy.numSetCells(preparedTinyGrid) should be(1)
      createStrategy.numSetCells(preparedSmallGrid) should be(16)

    }
    "should remove a pair of Cells" in {
      val removePairGrid = createStrategy.removePair(preparedSmallGrid, 0,0,3,3)
      removePairGrid.solved should be(false)
      removePairGrid.cell(0,0).value should be(0)
      removePairGrid.cell(3,3).value should be(0)
    }
    "should find a symmetric Cell" in {
      createStrategy.symmetricCell(4, 0,0) should be(3,3)
      createStrategy.symmetricCell(4, 0,3) should be(3,0)
    }
    "solve a Grid with a symmetric solution" in {
      val symmetricGrid = createStrategy.createNewGrid(4)
      symmetricGrid.isSymmetric should be(true)
    }
  }
}
