import scala.math.sqrt

case class Cell(value:Int) {
  def isSet:Boolean = value != 0
}

val cell1= Cell(2)
cell1.isSet

val cell2= Cell(0)
cell2.isSet

case class Grid(cells:Matrix[Cell]) {
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

case class Matrix[T] (rows:Vector[Vector[T]]) {
  def this(size:Int, filling:T) = this(Vector.tabulate(size, size){(row, col) => filling})
  val size:Int = rows.size
  def cell(row:Int, col:Int):T = rows (row)(col)
  def fill (filling:T):Matrix[T]= copy( Vector.tabulate(size, size){(row, col) => filling})
  def replaceCell(row:Int, col:Int, cell:T):Matrix[T] = copy(rows.updated(row, rows(row).updated(col, cell)))
}

case class House(cells:Vector[Cell])  {

}

val grid1 = new Grid(4)

grid1.cell(0,0).isSet
val grid2 = grid1.set(0,0,1)
grid2.cell(0,0).isSet
grid2.row(0)
grid2.col(0)
grid2.block(0)