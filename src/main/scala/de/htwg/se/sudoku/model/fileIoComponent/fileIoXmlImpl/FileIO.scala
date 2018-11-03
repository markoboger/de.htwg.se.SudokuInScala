package de.htwg.se.sudoku.model.fileIoComponent.fileIoXmlImpl

import com.google.inject.Guice
import com.google.inject.name.Names
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.sudoku.SudokuModule
import de.htwg.se.sudoku.model.fileIoComponent.FileIOInterface
import de.htwg.se.sudoku.model.gridComponent.GridInterface

import scala.xml.{NodeSeq, PrettyPrinter}
import scala.util.{Try, Success, Failure}

class FileIO extends FileIOInterface {

  override def load: Try[Option[GridInterface]] = {
    var gridOption: Option[GridInterface] = None

    Try {
      val file = scala.xml.XML.loadFile("grid.xml")
      val sizeAttr = (file \\ "grid" \ "@size")
      val size = sizeAttr.text.toInt
      val injector = Guice.createInjector(new SudokuModule)
      size match {
        case 1 => gridOption = Some(injector.instance[GridInterface](Names.named("tiny")))
        case 4 => gridOption = Some(injector.instance[GridInterface](Names.named("small")))
        case 9 => gridOption = Some(injector.instance[GridInterface](Names.named("normal")))
        case _ =>
      }
      val cellNodes = (file \\ "cell")
      gridOption match {
        case Some(grid) => {
          var _grid = grid
          for (cell <- cellNodes) {
            val row: Int = (cell \ "@row").text.toInt
            val col: Int = (cell \ "@col").text.toInt
            val value: Int = cell.text.trim.toInt
            _grid = _grid.set(row, col, value)
            val given = (cell \ "@given").text.toBoolean
            val showCandidates = (cell \ "@showCandidates").text.toBoolean
            if (given) _grid = _grid.setGiven(row, col, value)
            if (showCandidates) _grid = _grid.setShowCandidates(row, col)
          }
          gridOption = Some(_grid)
        }
        case None =>
      }
      gridOption
    }
  }

  def save(grid:GridInterface):Try[Unit] = saveString(grid)

  def saveXML(grid:GridInterface):Unit = {
    scala.xml.XML.save("grid.xml", gridToXml(grid))
  }

  def saveString(grid:GridInterface): Try[Unit] = {
    import java.io._

    Try {
      val pw = new PrintWriter(new File("grid.xml"))
      val prettyPrinter = new PrettyPrinter(120, 4)
      val xml = prettyPrinter.format(gridToXml(grid))
      pw.write(xml)
      pw.close
    }
  }

  def gridToXml(grid:GridInterface) = {
    <grid size ={grid.size.toString}>
      {
      for {
        row <- 0 until grid.size
        col <- 0 until grid.size
      } yield cellToXml(grid, row, col)
      }
    </grid>
  }

  def cellToXml(grid:GridInterface, row:Int, col:Int) ={
    <cell row ={row.toString} col={col.toString} given={grid.cell(row,col).given.toString} isHighlighted={grid.isHighlighted(row,col).toString} showCandidates={grid.cell(row, col).showCandidates.toString}>
      {grid.cell(row,col).value}
    </cell>
  }

}
