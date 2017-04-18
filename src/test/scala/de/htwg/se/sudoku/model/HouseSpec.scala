package de.htwg.se.sudoku.model

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class HouseSpec extends WordSpec with Matchers {

  "A new House with zero Cells" should {
    val house = House(Vector())
    "be valid" in {
      house.valid should be(true)
    }
    "produce an empty String " in {
      house.toString should be("")
    }
  }

  "A new House with one empty Cell" should {
    val house = House(Vector( Cell(0)))
    "be valid" in {
      house.valid should be(true)
    }
    "produce an String ' '" in {
      house.toString should be(" ")
    }
  }

  "A new House with one filled Cell" should {
    val cell0 = Cell(1)
    val house = House(Vector(cell0))
    "be valid" in {
      house.valid should be(true)
    }
    "produce an String 1" in {
      house.toString should be("1")
    }
    "return its cells by index" in {
      house.cells(0) should be(cell0)
    }
  }

  "A House with two identical Cells" should {
    val cell0 = Cell(1)
    val cell1 = Cell(1)
    val house = House(Vector(cell0, cell1))
    "be not valid" in {
      house.valid should be(false)
    }
    "return its cells by index" in {
      house.cells(0) should be(cell0)
      house.cells(1) should be(cell1)
    }
  }
  "A House with two different Cells" should {
    val cell0 = Cell(1)
    val cell1 = Cell(2)
    val house1 = House(Vector(cell0, cell1))
    val house2 = House(Vector(cell1, cell0))
    "be valid" in {
      house1.valid should be(true)
      house2.valid should be(true)
    }
  }
  "A House with four empty cells" should {
    val house = House(Vector.fill(4)(Cell(0)))
    "be valid" in {
      house.toIntList should be(List())
      house.toIntSet.toList should be(List())
      house.valid should be(true)
    }
  }
  "A House with four different cells" should {
    val house1 = House(Vector(Cell(1), Cell(2), Cell(3), Cell(4)))
    val house2 = House(Vector(Cell(3), Cell(1), Cell(2), Cell(4)))
    "be valid" in {
      house1.valid should be(true)
      house2.valid should be(true)
    }
  }
  "A House with nine different cells" should {
    val house1 = House(Vector(Cell(1), Cell(2), Cell(3), Cell(4), Cell(5), Cell(6), Cell(7), Cell(8), Cell(9)))
    val house2 = House(Vector(Cell(3), Cell(1), Cell(2), Cell(4), Cell(8), Cell(7), Cell(5), Cell(6), Cell(9)))
    val house3 = House(Vector(Cell(0), Cell(1), Cell(0), Cell(4), Cell(0), Cell(7), Cell(0), Cell(6), Cell(0)))
    "be valid" in {
      house1.valid should be(true)
      house2.valid should be(true)
      house3.valid should be(true)
    }
  }

}
