package de.htwg.se.sudoku


  import com.google.inject.AbstractModule
  import com.google.inject.name.Names
  import de.htwg.se.sudoku.controller.controllerComponent._
  import de.htwg.se.sudoku.model.gridComponent.GridInterface
  import de.htwg.se.sudoku.model.gridComponent.gridAdvancedImpl.Grid


  class SudokuModule extends AbstractModule {

    val defaultSize:Int = 9

    def configure() = {
      bindConstant().annotatedWith(Names.named("DefaultSize")).to(defaultSize)
      bind(classOf[GridInterface]).to(classOf[Grid])
      bind(classOf[ControllerInterface]).to(classOf[controllerBaseImpl.Controller])
    }

}
