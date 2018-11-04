package de.htwg.se.sudoku

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.se.sudoku.controller.controllerComponent._
import de.htwg.se.sudoku.model.fileIoComponent._
import de.htwg.se.sudoku.model.gridComponent.GridInterface
import de.htwg.se.sudoku.model.gridComponent.gridAdvancedImpl.Grid
import net.codingwell.scalaguice.ScalaModule

class SudokuModule extends AbstractModule with ScalaModule {

  val defaultSize: Int = 9

  def configure(): Unit = {
    bindConstant().annotatedWith(Names.named("DefaultSize")).to(defaultSize)
    bind[GridInterface].to[Grid]
    bind[ControllerInterface].to[controllerBaseImpl.Controller]

    bind[GridInterface].annotatedWithName("tiny").toInstance(new Grid(1))
    bind[GridInterface].annotatedWithName("small").toInstance(new Grid(4))
    bind[GridInterface].annotatedWithName("normal").toInstance(new Grid(9))

    bind[FileIOInterface].to[fileIoJsonImpl.FileIO]
  }

}

class MicroSudokuModule extends AbstractModule with ScalaModule {

  val defaultSize: Int = 9
  val defaultHostname: String = "localhost"
  val defaultFilePort: Int = 8089

  override def configure(): Unit = {
    bindConstant().annotatedWith(Names.named("DefaultSize")).to(defaultSize)
    bind[GridInterface].to[Grid]
    bind[ControllerInterface].to[controllerBaseImpl.Controller]

    bind[GridInterface].annotatedWithName("tiny").toInstance(new Grid(1))
    bind[GridInterface].annotatedWithName("small").toInstance(new Grid(4))
    bind[GridInterface].annotatedWithName("normal").toInstance(new Grid(9))

    bindConstant().annotatedWith(Names.named("FileHost")).to(defaultHostname)
    bindConstant().annotatedWith(Names.named("FilePort")).to(defaultFilePort)

    bind[FileIOInterface].to[fileIoMicroImpl.FileIO]
  }
}

class MongoDBModule extends AbstractModule with ScalaModule {

  val defaultSize: Int = 9
  val defaultHostname: String = "localhost"
  val defaultFilePort: Int = 27017

  override def configure(): Unit = {
    bindConstant().annotatedWith(Names.named("DefaultSize")).to(defaultSize)
    bind[GridInterface].to[Grid]
    bind[ControllerInterface].to[controllerBaseImpl.Controller]

    bind[GridInterface].annotatedWithName("tiny").toInstance(new Grid(1))
    bind[GridInterface].annotatedWithName("small").toInstance(new Grid(4))
    bind[GridInterface].annotatedWithName("normal").toInstance(new Grid(9))

    bindConstant().annotatedWith(Names.named("MongoDBHost")).to(defaultHostname)
    bindConstant().annotatedWith(Names.named("MongoDBPort")).to(defaultFilePort)

    bind[FileIOInterface].to[fileIoMongoDBImpl.FileIO]
  }
}

class SlickModule extends AbstractModule with ScalaModule {

  val defaultSize: Int = 9
  val databaseUrl: String = "jdbc:h2:~/sudoku-in-scala" // in memory on localhost
  val databaseUser: String = "SA"

  override def configure(): Unit = {
    bindConstant().annotatedWith(Names.named("DefaultSize")).to(defaultSize)
    bind[GridInterface].to[Grid]
    bind[ControllerInterface].to[controllerBaseImpl.Controller]

    bind[GridInterface].annotatedWithName("tiny").toInstance(new Grid(1))
    bind[GridInterface].annotatedWithName("small").toInstance(new Grid(4))
    bind[GridInterface].annotatedWithName("normal").toInstance(new Grid(9))

    bindConstant().annotatedWith(Names.named("H2Url")).to(databaseUrl)
    bindConstant().annotatedWith(Names.named("H2User")).to(databaseUser)

    bind[FileIOInterface].to[fileIoSlickImpl.FileIO]
  }
}
