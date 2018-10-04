package de.htwg.se.sudoku.model.playerComponent.playerBaseImpl

import de.htwg.se.sudoku.model.playerComponent.PlayerInterface

case class Player(name: String) extends PlayerInterface {
  override def toString: String = name
}
