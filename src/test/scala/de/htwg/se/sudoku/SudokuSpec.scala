package de.htwg.se.sudoku

import org.scalatest.{Matchers, WordSpec}

class SudokuSpec extends WordSpec with Matchers {

  "The Sudoku main class" should {
    "accept text input as argument without readline loop, to test it from command line " in {
      Sudoku.main(Array[String]("s"))
    }
  }

}
