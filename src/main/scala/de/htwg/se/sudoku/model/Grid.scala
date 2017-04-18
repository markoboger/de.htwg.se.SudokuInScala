package de.htwg.se.sudoku.model

import scala.math.sqrt
import scala.util.Random

case class Grid(cells: Matrix[Cell]) {
  def this(size: Int) = this(new Matrix[Cell](size, Cell(0)))

  val size: Int = cells.size
  val blocknum: Int = sqrt(size).toInt

  def cell(row: Int, col: Int): Cell = cells.cell(row, col)

  def set(row: Int, col: Int, value: Int): Grid = copy(cells.replaceCell(row, col, Cell(value)))

  def rows(row: Int): House = House(cells.rows(row))

  def allrows: IndexedSeq[House] = (0 until size).map(i => rows(i))

  def cols(col: Int): House = House(cells.rows.map(row => row(col)))

  def allcols: IndexedSeq[House] = (0 until size).map(i => cols(i))

  def blocks(block: Int): House = {
    House((for {
      row <- 0 until size
      col <- 0 until size; if blockAt(row, col) == block
    } yield cell(row, col)).asInstanceOf[Vector[Cell]])
  }

  def allblocks: IndexedSeq[House] = (0 until size).map(i => blocks(i))

  def blockAt(row: Int, col: Int): Int = (col / blocknum) + (row / blocknum) * blocknum

  def indexToRowCol(index: Int): (Int, Int) = {
    val r = index / size
    val c = index % size
    (r, c)
  }

  def valid: Boolean = allrows.forall(house => house.valid) && allcols.forall(house => house.valid) && allblocks.forall(house => house.valid)

  def available(row: Int, col: Int): Set[Int] = if (cell(row, col).isSet) {
    Set.empty
  }
  else {
    (1 to size).toSet -- rows(row).toIntSet -- cols(col).toIntSet -- blocks(blockAt(row, col)).toIntSet
  }

  def solved: Boolean = cells.rows.forall(coll => coll.forall(cell => cell.isSet))

  override def toString: String = {
    val lineseparator = ("+-" + ("--" * blocknum)) * blocknum + "+\n"
    val line = ("| " + ("x " * blocknum)) * blocknum + "|\n"
    var box = "\n" + (lineseparator + (line * blocknum)) * blocknum + lineseparator
    for {
      row <- 0 until size
      col <- 0 until size
    } box = box.replaceFirst("x", cell(row, col).toString)
    box
  }
}


