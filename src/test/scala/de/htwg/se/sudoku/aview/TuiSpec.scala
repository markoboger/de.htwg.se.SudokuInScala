package de.htwg.se.sudoku.aview

import de.htwg.se.sudoku.controller.Controller
import de.htwg.se.sudoku.model.Grid

import org.scalatest.{Matchers, WordSpec}

class TuiSpec  extends WordSpec with Matchers{

  "A Sudoku Tui" should {
    val controller = new Controller(new Grid(9))
    val tui = new Tui(controller)
    "do nothing on input 'q'" in {
      tui.processInputLine("q")
    }
    "create and empty Sudoku on input 'n'" in {
      tui.processInputLine("n")
      controller.grid should be(new Grid(9))
    }
    "set a cell on input '123'" in {
      tui.processInputLine("123")
      controller.grid.cell(1,2).value should be(3)
    }
    "undo on input 'z'" in {
      tui.processInputLine("123")
      tui.processInputLine("z")
      controller.grid.cell(1,2).value should be(0)
    }
    "redo on input 'y'" in {
      tui.processInputLine("123")
      tui.processInputLine("z")
      tui.processInputLine("y")
      controller.grid.cell(1,2).value should be(3)
    }
    "create a random Sudoku on input 'r'" in {
      tui.processInputLine("r")
      controller.grid.valid should be(true)
    }
    "solve a Sudoku on input 's'" in {
      tui.processInputLine("n")
      tui.processInputLine("s")
      controller.grid.solved should be(true)
    }
    "do nothing on bad input like'99999'" in {
      val old = controller.gridToString
      tui.processInputLine("99999")
      controller.gridToString should be(old)
    }
  }

}
