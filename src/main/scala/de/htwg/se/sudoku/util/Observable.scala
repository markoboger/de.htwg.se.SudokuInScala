package de.htwg.se.sudoku.util

import scala.swing.event.Event

trait Observer {
  def update: Unit
}

trait Observable {
  var subscribers: Vector[Observer] = Vector()

  def add(s: Observer): Unit = subscribers = subscribers :+ s

  def remove(s: Observer): Unit =
    subscribers = subscribers.filterNot(o => o == s)

  def notifyObservers: Unit = subscribers.foreach(o => o.update)

}
