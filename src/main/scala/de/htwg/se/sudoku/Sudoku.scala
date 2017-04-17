package de.htwg.se.sudoku

import de.htwg.se.sudoku.model.Player

object Sudoku {
  def main(args: Array[String]): Unit = {
    val student = Player("Your Name")
    println("Hello, " + student.name)
  }
}
