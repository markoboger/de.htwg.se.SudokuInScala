package de.htwg.se.sudoku.aview.gui


import de.htwg.se.sudoku.controller.controllerComponent.{CandidatesChanged, CellChanged, ControllerInterface, GridSizeChanged}
import de.htwg.se.sudoku.util.Observer

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._

class CellClicked(val row: Int, val column: Int) extends Event

class SwingGui(controller: ControllerInterface) extends Frame with Observer{

  listenTo(controller)

  title = "HTWG Sudoku"
  var cells = Array.ofDim[CellPanel](controller.gridSize, controller.gridSize)

  def highlightpanel = new FlowPanel {
    contents += new Label("Highlight:")
    for {index <- 0 to controller.gridSize} {
      val button = Button(if (index == 0) "" else index.toString) {
        controller.highlight(index)
      }
      button.preferredSize_=(new Dimension(30, 30))
      contents += button
      listenTo(button)
    }
  }

  def gridPanel = new GridPanel(controller.blockSize, controller.blockSize) {
    border = LineBorder(java.awt.Color.BLACK, 2)
    background = java.awt.Color.BLACK
    for {
      outerRow <- 0 until controller.blockSize
      outerColumn <- 0 until controller.blockSize
    } {
      contents += new GridPanel(controller.blockSize, controller.blockSize) {
        border = LineBorder(java.awt.Color.BLACK, 2)
        for {
          innerRow <- 0 until controller.blockSize
          innerColumn <- 0 until controller.blockSize
        } {
          val x = outerRow * controller.blockSize + innerRow
          val y = outerColumn * controller.blockSize + innerColumn
          val cellPanel = new CellPanel(x, y, controller)
          cells(x)(y) = cellPanel
          contents += cellPanel
          listenTo(cellPanel)
        }
      }
    }
  }
  val statusline = new TextField(controller.statusText, 20)

  contents = new BorderPanel {
    add(highlightpanel, BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("Empty") { controller.createEmptyGrid })
      contents += new MenuItem(Action("New") { controller.createNewGrid })
      contents += new MenuItem(Action("Save") {controller.save})
      contents += new MenuItem(Action("Load") {controller.load})
      contents += new MenuItem(Action("Quit") { System.exit(0) })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
            contents += new MenuItem(Action("Undo") { controller.undo })
            contents += new MenuItem(Action("Redo") { controller.redo })
    }
    contents += new Menu("Solve") {
      mnemonic = Key.S
      contents += new MenuItem(Action("Solve") { controller.solve })
    }
    contents += new Menu("Highlight") {
      mnemonic = Key.H
      for { index <- 0 to controller.gridSize } {
        contents += new MenuItem(Action(index.toString) { controller.highlight(index) })
      }
    }
    contents += new Menu("Options") {
      mnemonic = Key.O
      contents += new MenuItem(Action("Show all candidates") { controller.toggleShowAllCandidates })
      contents += new MenuItem(Action("Size 1*1") { controller.resize(1) })
      contents += new MenuItem(Action("Size 4*4") { controller.resize(4) })
      contents += new MenuItem(Action("Size 9*9") { controller.resize(9) })

    }
  }

  visible = true
  redraw

  reactions += {
    case event: GridSizeChanged => resize(event.newSize)
    case event: CellChanged     => redraw
    case event: CandidatesChanged => redraw
  }

  def resize(gridSize: Int) = {
    cells = Array.ofDim[CellPanel](controller.gridSize, controller.gridSize)
    contents = new BorderPanel {
      add(highlightpanel, BorderPanel.Position.North)
      add(gridPanel, BorderPanel.Position.Center)
      add(statusline, BorderPanel.Position.South)
    }
  }
  def redraw = {
    for {
      row <- 0 until controller.gridSize
      column <- 0 until controller.gridSize
    } cells(row)(column).redraw
    statusline.text = controller.statusText
    repaint
  }

  override def update: Unit = redraw
}