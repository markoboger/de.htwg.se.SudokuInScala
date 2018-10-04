package de.htwg.se.sudoku

import com.google.inject.{Guice, Injector}
import de.htwg.se.sudoku.aview.{GuiInterface, HttpServer, TuiInterface}
import de.htwg.se.sudoku.controller.controllerComponent.ControllerInterface
import de.htwg.se.sudoku.model.fileIoComponent.fileIoMicroImpl.FileIoHttpServer

import scala.io.StdIn.readLine

object Sudoku {
  val injector: Injector = Guice.createInjector(new MicroSudokuModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tui: TuiInterface = injector.getInstance(classOf[TuiInterface])
  val gui: GuiInterface = injector.getInstance(classOf[GuiInterface])

  val fileIoHttpServer: FileIoHttpServer = new FileIoHttpServer
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
  }
}
