package de.htwg.se.sudoku.controller

import scala.swing.event.Event

 class CellChanged extends Event
 case class GridSizeChanged(newSize: Int) extends Event
 class CandidatesChanged extends Event