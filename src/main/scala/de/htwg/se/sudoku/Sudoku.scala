package de.htwg.se.sudoku

import java.awt.GraphicsEnvironment
import java.io.BufferedReader

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.google.inject.{Guice, Injector}
import de.htwg.se.sudoku.aview.UiMessage.{Crash, CreateGui, CreateTui}
import de.htwg.se.sudoku.aview.{HttpServer, Tui, UiFactory}
import de.htwg.se.sudoku.controller.controllerComponent.ControllerInterface
import de.htwg.se.sudoku.model.fileIoComponent.fileIoMicroImpl.FileIoHttpServer

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.io.StdIn.readLine

object Sudoku {
  implicit val timeout: Timeout = Timeout(29.seconds) // used for akka ask pattern

  val injector: Injector = Guice.createInjector(new MicroSudokuModule)

  implicit val actorSystem = ActorSystem("actorSystem")
  val uiFactory = actorSystem.actorOf(Props[UiFactory])

  uiFactory ! Crash // demonstrate error / restart handling
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tuiFuture = uiFactory ? CreateTui(controller)
  val tui = Await.result(tuiFuture.mapTo[Tui], Duration.Inf)

  if (!GraphicsEnvironment.isHeadless) {
    uiFactory ! CreateGui(controller)
  }

  val fileIoHttpServer: FileIoHttpServer = injector.getInstance(classOf[FileIoHttpServer])
  val webserver = new HttpServer(controller)

  controller.createNewGrid

  def main(args: Array[String]): Unit = {
    tui.processInput(new BufferedReader(Console.in))

    webserver.unbind()
    fileIoHttpServer.unbind()
  }
}
