package de.htwg.se.sudoku.model

import scala.math.sqrt

case class Grid(private val cells:Matrix[Cell]) {
  def this(size:Int) = this(new Matrix[Cell](size, Cell(0)))
  val size:Int = cells.size
  def cell(row:Int, col:Int):Cell = cells.cell(row, col)
  def set(row:Int, col:Int, value:Int):Grid = copy(cells.replaceCell(row, col, Cell(value)))
  def row(row:Int):House = House(cells.rows(row))
  def col(col:Int):House = House(cells.rows.map(row=>row(col)))
  def block(block:Int):House = {
    val blocknum:Int = sqrt(size).toInt
    def blockAt(row: Int, col: Int):Int = (col / blocknum) + (row / blocknum) * blocknum
    House((for {
      row <- 0 until size
      col <- 0 until size; if blockAt(row, col) == block
    } yield cell(row, col)).asInstanceOf[Vector[Cell]])
  }
}


case class House(private val cells:Vector[Cell]) {
  def cell(index:Int):Cell = cells(index)
}