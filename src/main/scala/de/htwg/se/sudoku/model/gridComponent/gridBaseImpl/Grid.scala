package de.htwg.se.sudoku.model.gridComponent.gridBaseImpl

import de.htwg.se.sudoku.model.gridComponent.GridInterface

import scala.math.sqrt

case class Grid(cells: Matrix[Cell]) extends GridInterface {

  def this(size: Int) = this(new Matrix[Cell](size, Cell(0)))

  val size: Int = cells.size
  val blocknum: Int = sqrt(size).toInt

  def cell(row: Int, col: Int): Cell = cells.cell(row, col)

  def set(row: Int, col: Int, value: Int): Grid = copy(cells.replaceCell(row, col, Cell(value)))

  def reset(row: Int, col: Int): Grid = copy(cells.replaceCell(row, col, Cell(0, false, false, false)))

  def setGiven(row: Int, col: Int, value: Int): Grid = copy(cells.replaceCell(row, col, Cell(value, given = true)))

  def highlight(index: Int): Grid = {
    var grid = this
    for {
      row <- 0 until size
      col <- 0 until size
    } if (available(row, col).contains(index)) grid = grid.setHighlighted(row, col) else grid = grid.unsetHighlighted(row, col)
    grid
  }

  def setHighlighted(row: Int, col: Int): Grid = copy(cells.replaceCell(row, col, cell(row, col).copy(isHighlighted = true)))

  def unsetHighlighted(row: Int, col: Int): Grid = copy(cells.replaceCell(row, col, cell(row, col).copy(isHighlighted = false)))

  def isHighlighted(row: Int, col: Int) = cell(row, col).isHighlighted

  def setShowCandidates(row: Int, col: Int): Grid = copy(cells.replaceCell(row, col, cell(row, col).copy(showCandidates = true)))

  def isShowCandidates(row: Int, col: Int) = cell(row, col).showCandidates

  def unsetShowCandidates(row: Int, col: Int): Grid = copy(cells.replaceCell(row, col, cell(row, col).copy(showCandidates = false)))

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

  def isSymmetric: Boolean = {
    val resultList: IndexedSeq[Boolean] = for (row <- 0 until size; col <- 0 until size) yield {
      if ((cell(row, col).isSet == true && symmetricCell(row, col).isSet == true) || (cell(row, col).isSet == false && symmetricCell(row, col).isSet == false)) true else false
    }
    resultList.forall(_ == true)
  }

  def symmetricCell(row: Int, col: Int): Cell = cell(size - 1 - row, size - 1 - col)

  def available(row: Int, col: Int): Set[Int] = if (cell(row, col).isSet) {
    Set.empty
  } else {
    (1 to size).toSet -- rows(row).toIntSet -- cols(col).toIntSet -- blocks(blockAt(row, col)).toIntSet
  }

  def solved: Boolean = cells.rows.forall(coll => coll.forall(cell => cell.isSet))

  def markFilledCellsAsGiven: Grid = {
    var tempGrid = this
    for {
      row <- 0 until size
      col <- 0 until size; if cell(row, col).isSet
    } tempGrid = tempGrid.setGiven(row, col, cell(row, col).value)
    tempGrid
  }

  override def toString: String = {
    val lineseparator = ("+-" + ("--" * blocknum)) * blocknum + "+\n"
    val line = ("| " + ("x " * blocknum)) * blocknum + "|\n"
    var box = "\n" + (lineseparator + (line * blocknum)) * blocknum + lineseparator
    for {
      row <- 0 until size
      col <- 0 until size
    } box = box.replaceFirst("x ", cell(row, col).toString)
    box
  }

  override def createNewGrid: GridInterface = (new GridCreateRandomStrategy).createNewGrid(size)

  override def solve: (Boolean, GridInterface) = new Solver(this).solve
}

object Grid {
  import play.api.libs.json._
  implicit val gridWrites = Json.writes[Grid]
  implicit val gridReads = Json.reads[Grid]
}