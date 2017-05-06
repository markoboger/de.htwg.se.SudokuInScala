package de.htwg.se.sudoku.controller

object GameStatus extends Enumeration{
  type GameStatus = Value
  val IDLE, SOLVED, NOT_SOLVABLE = Value

  val map = Map[GameStatus, String](
    IDLE -> "",
    SOLVED ->"Game successfully solved",
    NOT_SOLVABLE ->"Game not solvable")

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }

}
