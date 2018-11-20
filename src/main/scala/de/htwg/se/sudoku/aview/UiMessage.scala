package de.htwg.se.sudoku.aview

import de.htwg.se.sudoku.controller.controllerComponent.ControllerInterface

object UiMessage {
  case class CreateGui(controller: ControllerInterface)
  case class CreateTui(controller: ControllerInterface)
  case class Crash()
}
