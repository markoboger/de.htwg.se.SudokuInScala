package de.htwg.se.sudoku.model

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GridSpec extends WordSpec with Matchers {
  "A Grid is the playingfield of Sudoku. A Grid" when {
    "to be constructed" should {
      "be created with the length of its edges as size. Practically relevant are size 1, 4, and 9" in {
        val tinygrid = new Grid(1)
        val smallGrid = new Grid(4)
        val normalGrid = new Grid(9)
        val awkwardGrid = new Grid(2)
      }
      "for test purposes only created with a Matrix of Cells" in {
        val awkwardGrid = Grid(new Matrix(2, Cell(0)))
        val testGrid = Grid(Matrix[Cell](Vector(Vector(Cell(0), Cell(0)), Vector(Cell(0), Cell(0)))))
      }
    }
    "created properly but empty" should {
      val tinygrid = new Grid(1)
      val smallGrid = new Grid(4)
      val normalGrid = new Grid(9)
      val awkwardGrid = new Grid(2)
      "give access to its Cells" in {
        tinygrid.cell(0, 0) should be(Cell(0))
        smallGrid.cell(0, 0) should be(Cell(0))
        smallGrid.cell(0, 1) should be(Cell(0))
        smallGrid.cell(1, 0) should be(Cell(0))
        smallGrid.cell(1, 1) should be(Cell(0))
      }
      "allow to set individual Cells and remain immutable" in {
        val changedGrid = smallGrid.set(0, 0, 1)
        changedGrid.cell(0, 0) should be(Cell(1))
        smallGrid.cell(0, 0) should be(Cell(0))
      }
    }
    "prefilled with values 1 to n" should {
      val tinyGrid = Grid(new Matrix[Cell](Vector(Vector(Cell(1)))))
      val smallGrid = Grid(new Matrix[Cell](Vector(Vector(Cell(1), Cell(2)), Vector(Cell(3), Cell(4)))))
      "have the right values in the right places" in {
        smallGrid.cell(0, 0) should be(Cell(1))
        smallGrid.cell(0, 1) should be(Cell(2))
        smallGrid.cell(1, 0) should be(Cell(3))
        smallGrid.cell(1, 1) should be(Cell(4))
      }
      "have Houses with the right Cells" in {
        tinyGrid.rows(0).cells(0) should be(Cell(1))
        tinyGrid.cols(0).cells(0) should be(Cell(1))
        tinyGrid.blocks(0).cells(0) should be(Cell(1))

        smallGrid.rows(0).cells(0) should be(Cell(1))
        smallGrid.rows(0).cells(1) should be(Cell(2))
        smallGrid.rows(1).cells(0) should be(Cell(3))
        smallGrid.rows(1).cells(1) should be(Cell(4))
        smallGrid.cols(0).cells(0) should be(Cell(1))
        smallGrid.cols(0).cells(1) should be(Cell(3))
        smallGrid.cols(1).cells(0) should be(Cell(2))
        smallGrid.cols(1).cells(1) should be(Cell(4))
      }
    }
    "prefilled with 1 to n on the diagonal" should {
      val normalGrid = new Grid(9)
      val diagonalGrid = normalGrid.set(0, 0, 1).set(1, 1, 2).set(2, 2, 3).set(3, 3, 4).set(4, 4, 5).set(5, 5, 6).set(6, 6, 7).set(7, 7, 8).set(8, 8, 9)
      "have blocks with the right cells" in {
        diagonalGrid.blocks(0).cells(0) should be(Cell(1))
        diagonalGrid.blocks(0).cells(4) should be(Cell(2))
        diagonalGrid.blocks(0).cells(8) should be(Cell(3))
        diagonalGrid.blocks(4).cells(0) should be(Cell(4))
        diagonalGrid.blocks(4).cells(4) should be(Cell(5))
        diagonalGrid.blocks(4).cells(8) should be(Cell(6))
        diagonalGrid.blocks(8).cells(0) should be(Cell(7))
        diagonalGrid.blocks(8).cells(4) should be(Cell(8))
        diagonalGrid.blocks(8).cells(8) should be(Cell(9))
      }
    }
  }

}
