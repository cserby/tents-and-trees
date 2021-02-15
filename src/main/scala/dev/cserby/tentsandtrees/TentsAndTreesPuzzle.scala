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

object TentsAndTreesPuzzle {
  implicit val tentsAndTreesPuzzleJsonRw: upickle.default.ReadWriter[TentsAndTreesPuzzle] = upickle.default.macroRW

  def apply(
    inputTable: Array[Array[Char]],
    rowSums: Array[Int],
    colSums: Array[Int]) = {
      new TentsAndTreesPuzzle(inputTable, rowSums, colSums)
  }

  def apply(
    brainBashersTable: String,
    brainBashersRowSums: String,
    brainBashersColSums: String
  ) = {
    new TentsAndTreesPuzzle(
      inputTable = dailyTentsTableToInputTable(brainBashersTable),
      rowSums = brainBashersRowSums.toCharArray().map(_.asDigit),
      colSums = brainBashersColSums.toCharArray().map(_.asDigit)
    )
  }

  def dailyTentsTableToInputTable(input: String): Array[Array[Char]] = {
    def dailyTentsTableToInputTable(input: Array[Char]): Array[Array[Char]] = {
      def splitPer(a: Array[Char], n: Integer, acc: Array[Array[Char]]): Array[Array[Char]] =
        a.splitAt(n) match {
          case (Array(), _) => acc
          case (h, t) => splitPer(t, n, acc :+ h.map{
            case '1' => 't'
            case '2' => 'T'
            case _ => ' '
          })
        }

      def tableSize = math.sqrt(input.length).toInt
      splitPer(input, tableSize, new Array[Array[Char]](0))
    }

    dailyTentsTableToInputTable(input.toCharArray())
  }
}