package de.htwg.se.sudoku.model.database

import slick.jdbc.H2Profile.api._

class GridSchema(tag: Tag) extends Table[(Int, Int)](tag, "GRID") {
  def id = column[Int]("GRID_ID", O.PrimaryKey, O.AutoInc)
  def size = column[Int]("SIZE")
  def * = (id, size)
}

