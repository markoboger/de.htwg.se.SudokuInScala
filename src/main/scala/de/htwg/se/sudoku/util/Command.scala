package de.htwg.se.sudoku.util

trait Command {

  def doStep:Unit
  def undoStep:Unit
  def redoStep:Unit

}

class UndoManager {
  private var undoStack: List[Command]= Nil
  private var redoStack: List[Command]= Nil
  def doStep(command: Command) = {
    undoStack = command::undoStack
    command.doStep
  }
  def undoStep  = {
    undoStack match {
      case  Nil =>
      case head::stack => {
        head.undoStep
        undoStack=stack
        redoStack= head::redoStack
      }
    }
  }
  def redoStep = {
    redoStack match {
      case Nil =>
      case head::stack => {
        head.redoStep
        redoStack=stack
        undoStack=head::undoStack
      }
    }
  }
}