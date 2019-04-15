package dev.cserby.tentsandtrees

case class TentsAndTreesSolution(table: Matrix[Option[Int]]) {
  override def toString: String = {
    (0 until table.rows).map{ row =>
      (0 until table.cols).map{ col =>
        table.get(row, col).flatten match {
          case Some(_) => 't'
          case None =>
            if (
              table.get(row - 1, col).flatten.contains(2) ||
              table.get(row, col - 1).flatten.contains(1) ||
              table.get(row, col + 1).flatten.contains(3) ||
              table.get(row + 1, col).flatten.contains(4)
            ) "T"
            else " "
        }
      }.mkString("|")
    }.mkString("\n")
  }
}