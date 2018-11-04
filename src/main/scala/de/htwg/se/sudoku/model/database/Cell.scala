package de.htwg.se.sudoku.model.database

import slick.jdbc.H2Profile.api._

class Cell(tag: Tag) extends Table[(Int, Int, Int, Int, Boolean, Boolean)](tag, "CELL") {
  def id = column[Int]("CELL_ID", O.PrimaryKey, O.AutoInc)
  def row = column[Int]("ROW")
  def col = column[Int]("COL")
  def value = column[Int]("VALUE")
  def given = column[Boolean]("GIVEN")
  def showCandidates = column[Boolean]("SHOW_CANDIDATES")
  def * = (id, row, col, value, given, showCandidates)
}
