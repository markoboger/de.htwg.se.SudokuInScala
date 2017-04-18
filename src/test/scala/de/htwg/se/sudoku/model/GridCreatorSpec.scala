package de.htwg.se.sudoku.model

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GridCreatorSpec extends WordSpec with Matchers {

  "A GridCreator " should {
    "create an empty Grid and fill it with cells with a creation strategy" in {
      val tinyGrid = new GridCreator(1).createRandom(1)
      tinyGrid.cell(0,0).value should be(1)

      val smallGrid = new GridCreator(4).createRandom(8)
      smallGrid.valid should be(true)

      val normalGrid = new GridCreator(9).createRandom(40)
      normalGrid.valid should be(true)
    }
  }

}
