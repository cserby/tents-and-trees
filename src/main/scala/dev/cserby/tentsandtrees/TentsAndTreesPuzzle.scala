package dev.cserby.tentsandtrees


case class TentsAndTreesPuzzle(
             inputTable: Array[Array[Char]],
             rowSums: Array[Int],
             colSums: Array[Int]) {

  require(inputTable.length > 0)
  require(inputTable.forall(_.length > 0))
  require(inputTable.forall(_.length == inputTable.head.length))
  require(rowSums.length == inputTable.length)
  require(colSums.length == inputTable.head.length)
}