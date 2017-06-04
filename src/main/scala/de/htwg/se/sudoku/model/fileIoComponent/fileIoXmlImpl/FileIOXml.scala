package de.htwg.se.sudoku.model.fileIoComponent.fileIoXmlImpl

import de.htwg.se.sudoku.model.fileIoComponent.FileIO
import de.htwg.se.sudoku.model.gridComponent.GridInterface
import de.htwg.se.sudoku.model.gridComponent.gridBaseImpl.Grid

import scala.xml.{NodeSeq, PrettyPrinter}

class FileIOXml(grid:GridInterface) extends FileIO {

  override def load: GridInterface = {
    val file = scala.xml.XML.loadFile("grid.xml")
    val sizeAttr = (file \\ "grid" \ "@size")
    val size = sizeAttr.text.toInt
    var grid = new Grid(size)

    val cellNodes= (file \\ "cell")
    for (cell <- cellNodes) {
      val row:Int = (cell \ "@row").text.toInt
      val col:Int = (cell \ "@col").text.toInt
      val value:Int = cell.text.trim.toInt
      grid = grid.set(row, col, value)

      println("row =" + row + " col= "+ col + " value =" + value)
    }
    grid
  }

  def save:Unit = saveString

  def saveXML:Unit = {
    scala.xml.XML.save("grid.xml", gridToXml)
  }

  def saveString: Unit = {
    import java.io._
    val pw = new PrintWriter(new File("grid.xml" ))
    val prettyPrinter = new PrettyPrinter(120,4)
    val xml = prettyPrinter.format(gridToXml)
    pw.write(xml)
    pw.close
  }
  def gridToXml = {
    <grid size ={grid.size.toString}>
      {
      for {
        row <- 0 until grid.size
        col <- 0 until grid.size
      } yield cellToXml(row, col)
      }
    </grid>
  }

  def cellToXml(row:Int, col:Int) ={
    <cell row ={row.toString} col={col.toString} given={grid.cell(row,col).given.toString} isHighlighted={grid.isHighlighted(row,col).toString} showCandidates={grid.cell(row, col).showCandidates.toString}>
      {grid.cell(row,col).value}
    </cell>
  }

  def xmlToGrid = ???
  def xmlToCell = ???
}
