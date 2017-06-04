package de.htwg.se.sudoku.model.fileIoComponent.fileIoXmlImpl

import de.htwg.se.sudoku.model.gridComponent.gridAdvancedImpl.Grid

object ioWorksheet {
  1 + 2
  val size = 9

  def toXml = {
    <grid size={size.toString}>
    </grid>
  }

  println(toXml)

  val grid = new Grid(1)
  val filledGrid = grid.set(0, 0, 1)
  println(filledGrid.toString)

  val fileIO = new FileIO(filledGrid)
  val xml = fileIO.gridToXml

}