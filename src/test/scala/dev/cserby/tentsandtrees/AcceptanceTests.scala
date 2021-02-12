package dev.cserby.tentsandtrees

import org.scalactic.source.Position
import org.scalatest.{FunSpec, Matchers}
import oscar.algo.search.SearchStatistics

class AcceptanceTests extends FunSpec with Matchers {
  def tentsAndTreesTest(
                         inputTable: Array[Array[Char]],
                         rowSums: Array[Int],
                         colSums: Array[Int]
                       )(implicit pos: Position): Unit = {
    val (stats, solutions): (SearchStatistics, Seq[TentsAndTreesSolution]) =
      TentsAndTreesSolver.solve(TentsAndTreesPuzzle(
        inputTable,
        rowSums,
        colSums
      ))

    solutions.length shouldEqual 1
    solutions.headOption.map(_.toString) shouldEqual Some(inputTable.map(_.mkString("|")).mkString("\n"))

    println(s"Stats: $stats")
    println(s"Solutions:")
    solutions.map(println)
  }


  describe("Tents and Trees solver") {
    it("3x3 easy") {
      tentsAndTreesTest(
        inputTable = Array(
          Array('t', ' ', ' '),
          Array('T', ' ', 'T'),
          Array(' ', ' ', 't')
        ),
        rowSums = Array(0, 2, 0),
        colSums = Array(1, 0, 1)
      )
    }

    it("8x8 easy") {
      tentsAndTreesTest(
        inputTable = Array(
          " t T    ",
          " T t TtT",
          "   t   t",
          "   T    ",
          "T   tTtT",
          "t Tt   t",
          "     TtT",
          "tT      "
        ).map(_.toCharArray),
        rowSums = Array(1, 3, 0, 1, 3, 1, 2, 1),
        colSums = Array(1, 2, 1, 2, 0, 3, 0, 3)
      )
    }

    it("20x20 hard") {
      tentsAndTreesTest(
        inputTable = Array(
          "TtT Ttt   Ttt   tT T",
          "tt    T T   T tT   t",
          " T T t  t         tT",
          "   t T TtTttT T  T t",
          "Tt    t       t  t T",
          "  tT  T  TtTtTtT    ",
          "          t   tt  Tt",
          "Tt   Tt   T T T t   ",
          " t Tt  T    t   T  t",
          " T  t  t Ttt Tt    T",
          "    T T    T ttTtT  ",
          " T    tt T   T    t ",
          " t Tt  T t        T ",
          "Tt  tT     T  T     ",
          " tT  t T T t  t t   ",
          " t   T t t   T  T  T",
          " T       tT tt   t t",
          " ttT  TtT   T T  T T",
          " T   tt   t t t t  t",
          "  tT T Tt T T TtTtT "
        ).map(_.toCharArray),
        rowSums = Array(6, 4, 3, 6, 2, 6, 1, 5, 3, 4, 5, 3, 3, 4, 3, 4, 2, 7, 1, 8),
        colSums = Array(4, 5, 2, 6, 2, 5, 4, 5, 2, 5, 4, 3, 5, 4, 5, 3, 3, 4, 3, 6)
      )
    }

    it("TentsAndTrees C - 13x13") {
      tentsAndTreesTest(
        inputTable = Array(
          "tT        Tt ",
          "t tTtT  Ttt T",
          "T     t   T t",
          "    T T Tt tT",
          " T  t   t Tt ",
          " t tT   T t  ",
          " T    T t TtT",
          " t Tt t T   t",
          "tT          T",
          "   TtT ttT   ",
          "     t T    T",
          "   TtT t t  t",
          "Tt   t T TtT "
        ).map(_.toCharArray),
        rowSums = Array(2, 4, 2, 4, 2, 2, 4, 2, 2, 3, 2, 2, 4),
        colSums = Array(2, 4, 0, 4, 2, 3, 2, 2, 4, 2, 4, 1, 5)
      )
    }

    it("TentsAndTrees C - 13x13 - 2") {
      tentsAndTreesTest(
        inputTable = Array(
            "            T",
            "T tT  TtTtT t",
            "t     tt     ",
            "  TtTt T T tT",
            "T        t   ",
            "t T Tt    tT ",
            "  t   T  Tt  ",
            "T T   t    Tt",
            "t ttT    Tt  ",
            " T    tT   tT",
            " t TtTt  tT  ",
            "      tT  ttT",
            " TtTtTt   T  "
        ).map(_.toCharArray),
        rowSums = Array(1, 5, 0, 5, 1, 3, 2, 3, 2, 3, 3, 2, 4),
        colSums = Array(3, 2, 3, 3, 3, 2, 2, 3, 1, 3, 3, 2, 4)
      )
    }

    it("TentsAndTrees C - 13x13 - 3") {
      tentsAndTreesTest(
        inputTable = Array(
          " t TtT T    T",
          " T   t t T  t",
          "      t  t tT",
          " tT T T T Tt ",
          "Tt  t  tt   T",
          "   TtT T Tt t",
          "Tt  tt      T",
          "t T T tT T  t",
          "T tt  t  t   ",
          "   T  T     T",
          "        tT  t",
          " T t    t tT ",
          " t T tT T    ",
        ).map(_.toCharArray),
        rowSums = Array(4, 2, 1, 5, 2, 4, 2, 4, 1, 3, 1, 2, 3),
        colSums = Array(3, 2, 2, 4, 2, 2, 3, 3, 2, 4, 1, 1, 5)
      )
    }

    it("TentsAndTrees C - 15x15") {
      tentsAndTreesTest(
        inputTable = Array(
          "T tT Tt   TtT T",
          "t      T  t  tt",
          " Tt t  t TtTtT ",
          "    T t     t t",
          "      T     T T",
          "  Tt     tT  t ",
          "Tt     T     T ",
          " t   Ttt tT    ",
          " TtT   T      T",
          "       t      t",
          "t T    TtTtT  T",
          "T t      t    t",
          "  TttTtT     tT",
          "Tt t     t Ttt ",
          "   T TtTtT   T "
        ).map(_.toCharArray),
        rowSums = Array(6, 1, 4, 1, 3, 2, 3, 2, 4, 0, 5, 1, 4, 2, 5),
        colSums = Array(4, 2, 3, 3, 1, 4, 1, 6, 0, 3, 3, 3, 2, 3, 5)
      )
    }

    it("TentsAndTrees B - 18x18") {
      tentsAndTreesTest(
        inputTable = Array(
          " t   Tt T TtTtT T ",
          " T Tt   t     t t ",
          "t              Ttt",
          "T  Tt TtT  TtT   T",
          "      t      ttT  ",
          "tT T  tT  t  Tt  t",
          "t  t T    T    TtT",
          "T Tt t Tt    T    ",
          "t   tT       ttTtT",
          "TtT  t  T  T T   t",
          "     T  tt t ttT T",
          "  T    t T T     t",
          "T t    T   t  T  T",
          "t   T t tT  t t t ",
          "tT  t T     T   T ",
          "   Tt     T   T   ",
          " T   T tT t   t t ",
          " ttT t     TtTt T "
        ).map(_.toCharArray),
        rowSums = Array(6, 2, 1, 7, 1, 4, 4, 4, 3, 5, 3, 3, 4, 2, 4, 3, 3, 4),
        colSums = Array(4, 4, 3, 5, 1, 5, 2, 3, 4, 2, 3, 4, 2, 5, 3, 5, 3, 5)
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

    it("BrainBashers - 2021 Feb 12 - 20x20") {
      tentsAndTreesTest(
        inputTable = dailyTentsTableToInputTable("0001012020001202021202120000100210010100000000000210120212000200002100021011000201020001200000202101021100010002100000011000120200000212020220211000012001001100000020012000021020000000002101021010001202120000020000202100000100000001000001022120002121020200020110002100000001021000000100001120001000022002000020002020200110001200010110001012200210021202120012011000101000100012010202102021202002100200"),
        rowSums = "63444263435172262727".toCharArray().map(_.asDigit),
        colSums = "45253252633617354608".toCharArray().map(_.asDigit)
      )
    }

    it("BrainBashers - 2021 Feb 12 - 16 x 16 Medium") {
      tentsAndTreesTest(
        inputTable = dailyTentsTableToInputTable("0212020210100210000101000020000202120001001021010100120200200120020000000001200001020210012001012001001200000202101202000120000002000102000000020100021112000201210000000000010001212020020212002100001001000112000002121012120012000000020000000002121001012012"),
        rowSums = "5234234333162424".toCharArray().map(_.asDigit),
        colSums = "3515171503423515".toCharArray().map(_.asDigit)
      )
    }
  }
}
