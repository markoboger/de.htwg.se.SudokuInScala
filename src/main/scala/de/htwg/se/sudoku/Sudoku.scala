package de.htwg.se.sudoku

import com.google.inject.{Guice, Injector}
import de.htwg.se.sudoku.aview.gui.SwingGui
import de.htwg.se.sudoku.aview.{HttpServer, Tui}
import de.htwg.se.sudoku.controller.controllerComponent.ControllerInterface
import de.htwg.se.sudoku.model.fileIoComponent.FileIOInterface
import de.htwg.se.sudoku.model.fileIoComponent.fileIoMicroImpl.FileIoHttpServer

import scala.io.StdIn.readLine

object Sudoku {
  val injector: Injector = Guice.createInjector(new MicroSudokuModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tui = new Tui(controller)
  val gui = new SwingGui(controller)

  val fileIoHttpServer: FileIoHttpServer = injector.getInstance(classOf[FileIoHttpServer])
  val webserver = new HttpServer(controller)

  controller.createNewGrid

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
    webserver.unbind()
    fileIoHttpServer.unbind()
    controller.finish()
  }
}
