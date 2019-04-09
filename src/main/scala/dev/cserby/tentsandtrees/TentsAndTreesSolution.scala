package dev.cserby.tentsandtrees

case class TentsAndTreesSolution(table: Array[Array[Option[Int]]]) {
  override def toString: String = {
    table.map{ row =>
      row.map {
        case None => " "
        case Some(tree) => tree.toString
      }.mkString("|")
    }.mkString("\n")
  }
}