package de.htwg.se.sudoku.model

case class House(cells: Vector[Cell]) {

  def valid: Boolean = this.toIntList == this.toIntSet

  def toIntSet: List[Int] = cells.filterNot(_.value == 0).map(_.value).toSet.toList.sorted

  def toIntList: List[Int] = cells.filterNot(_.value == 0).map(_.value).toList.sorted

  override def toString: String = cells.mkString
}
