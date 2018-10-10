package de.htwg.se.sudoku.controller

import de.htwg.se.sudoku.model.Grid
import de.htwg.se.sudoku.util.Observer

import scala.language.reflectiveCalls
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends WordSpec with Matchers {

  "A Controller" when {
    "observed by an Observer" should {
      val smallGrid = new Grid(4)
      val controller = new Controller(smallGrid)
      val observer = new Observer {
        var updated: Boolean = false
        def isUpdated: Boolean = updated
        override def update: Unit = updated = true
      }
      controller.add(observer)
      "notify its Observer after creation" in {
        controller.createEmptyGrid(4)
        observer.updated should be(true)
        controller.grid.size should be(4)
      }
      "notify its Observer after random creation" in {
        controller.createRandomGrid(4, 1)
        observer.updated should be(true)
        controller.grid.valid should be(true)
      }
      "notify its Observer after setting a cell" in {
        controller.set(1,1,4)
        observer.updated should be(true)
        controller.grid.cell(1,1).value should be (4)
      }
      "notify its Observer after solving" in {
        controller.solve
        observer.updated should be(true)
        controller.grid.solved should be(true)
      }
    }
  }
}
