package de.htwg.se.sudoku.aview

import de.htwg.se.sudoku.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.sudoku.model.gridComponent.gridBaseImpl.Grid

import de.htwg.se.sudoku.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.sudoku.model.gridComponent.gridBaseImpl.Grid

import org.scalatest.{Matchers, WordSpec}

class TuiSpec  extends WordSpec with Matchers{

  "A Sudoku Tui" should {
    val controller = new Controller(new Grid(9))
    val tui = new Tui(controller)
    "create and empty Sudoku on input 'e'" in {
      tui.processInputLine("e")
      controller.grid should be(new Grid(9))
    }
    "set a cell on input '123'" in {
      tui.processInputLine("123")
      controller.grid.cell(1,2).value should be(3)
    }
    "create a new Sudoku on input 'n'" in {
      tui.processInputLine("n")
      controller.grid.valid should be(true)
    }
    "solve a Sudoku on input 's'" in {
      tui.processInputLine("n")
      tui.processInputLine("s")
      controller.grid.solved should be(true)
    }
  }

}
