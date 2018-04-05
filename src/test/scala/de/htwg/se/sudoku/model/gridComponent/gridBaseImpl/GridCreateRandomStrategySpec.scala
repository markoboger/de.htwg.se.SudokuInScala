package de.htwg.se.sudoku.model.gridComponent.gridBaseImpl

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class GridCreateRandomStrategySpec extends WordSpec with Matchers {

  "A GridCreator " should {
    "create an empty Grid and fill it with cells with a creation strategy" in {
      val tinyGrid = (new GridCreateRandomStrategy).createNewGrid(1)
      tinyGrid.cell(0, 0).value should be(1)

      val smallGrid = (new GridCreateRandomStrategy).createNewGrid(4)
      smallGrid.valid should be(true)

      val normalGrid = (new GridCreateRandomStrategy).createNewGrid(9)
      normalGrid.valid should be(true)
    }
  }

}
