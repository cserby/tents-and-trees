package dev.cserby.tentsandtrees

case class Matrix[T](contents: Array[Array[T]]) {
  require(contents.length > 0)
  require(contents.forall(_.length > 0))
  require(contents.forall(_.length == contents.head.length))

  lazy val transposed: Matrix[T] = new Matrix[T](contents.transpose)
  val rows: Int = contents.length
  val cols: Int = contents.head.length

  def get(row: Int, col: Int): Option[T] = {
    getRow(row).flatMap(_.lift(col))
  }

  def getRow(row: Int): Option[Array[T]] = {
    contents.lift(row)
  }

  def getCol(col: Int): Option[Array[T]] = {
    transposed.getRow(col)
  }
}