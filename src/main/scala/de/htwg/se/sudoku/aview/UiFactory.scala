package de.htwg.se.sudoku.aview

import akka.actor.Actor
import de.htwg.se.sudoku.aview.UiMessage.{Crash, CreateGui, CreateTui}
import de.htwg.se.sudoku.aview.gui.SwingGui

case class UiFactory() extends Actor {

  override def receive: Receive = {

    case CreateGui(controller) => new SwingGui(controller)
    case CreateTui(controller) =>
      val tui = new Tui(controller)
      sender ! tui
    case Crash => throw new NullPointerException() // actor gets restarted by default
  }
}
