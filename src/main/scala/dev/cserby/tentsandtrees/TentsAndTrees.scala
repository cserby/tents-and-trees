package dev.cserby.tentsandtrees

object TentsAndTrees extends App {
/*  val inputTable: Array[Array[Char]] = Array(
    Array('t', ' ', ' '),
    Array(' ', ' ', ' '),
    Array(' ', ' ', 't')
  )

  val rowSums: Array[Int] = Array(0, 2, 0)
  val colSums: Array[Int] = Array(1, 0, 1)*/

  /*val rowSums: Array[Int] = Array(6, 4, 3, 6, 2, 6, 1, 5, 3, 4, 5, 3, 3, 4, 3, 4, 2, 7, 1, 8)
  val colSums: Array[Int] = Array(4, 5, 2, 6, 2, 5, 4, 5, 2, 5, 4, 3, 5, 4, 5, 3, 3, 4, 3, 6)

  val inputTable: Array[Array[Char]] = Array(
    " t   tt    tt   t   ",
    "tt            t    t",
    "     t  t         t ",
    "   t    t tt       t",
    " t    t       t  t  ",
    "  t       t t t     ",
    "          t   tt   t",
    " t    t         t   ",
    " t  t       t      t",
    "    t  t  tt  t     ",
    "             tt t   ",
    "      tt          t ",
    " t  t    t          ",
    " t  t               ",
    " t   t     t  t t   ",
    " t     t t          ",
    "         t  tt   t t",
    " tt    t            ",
    "     tt   t t t t  t",
    "  t     t      t t  "
  ).map(_.toCharArray)*/

  val rowSums: Array[Int] = Array(1, 3, 0, 1, 3, 1, 2, 1)
  val colSums: Array[Int] = Array(1, 2, 1, 2, 0, 3, 0, 3)

  val inputTable: InputTable = Array(
    " t      ",
    "   t  t ",
    "   t   t",
    "        ",
    "    t t ",
    "t  t   t",
    "      t ",
    "t       "
  ).map(_.toCharArray)

  println(TentsAndTreesPuzzle(inputTable, rowSums, colSums).solve)
}
