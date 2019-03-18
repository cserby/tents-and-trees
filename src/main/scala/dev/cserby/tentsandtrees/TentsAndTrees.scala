package dev.cserby.tentsandtrees

import oscar.cp.{CPIntVar, CPModel, Constraint, add, binaryFirstFail, search, start}

object TentsAndTrees extends CPModel with App {
/*  val inputTable: Array[Array[Char]] = Array(
    Array('t', ' ', ' '),
    Array(' ', ' ', ' '),
    Array(' ', ' ', 't')
  )

  val rowSums: Array[Int] = Array(0, 2, 0)
  val colSums: Array[Int] = Array(1, 0, 1)*/

  val rowSums: Array[Int] = Array(6, 4, 3, 6, 2, 6, 1, 5, 3, 4, 5, 3, 3, 4, 3, 4, 2, 7, 1, 8)
  val colSums: Array[Int] = Array(4, 5, 2, 6, 2, 5, 4, 5, 2, 5, 4, 3, 5, 4, 5, 3, 3, 4, 3, 6)

  val inputTable: Array[Array[Char]] = Array(
    " t   tt    tt   t   ".toCharArray,
    "tt            t    t".toCharArray,
    "     t  t         t ".toCharArray,
    "   t    t tt       t".toCharArray,
    " t    t       t  t  ".toCharArray,
    "  t       t t t     ".toCharArray,
    "          t   tt   t".toCharArray,
    " t    t         t   ".toCharArray,
    " t  t       t      t".toCharArray,
    "    t  t  tt  t     ".toCharArray,
    "             tt t   ".toCharArray,
    "      tt          t ".toCharArray,
    " t  t    t          ".toCharArray,
    " t  t               ".toCharArray,
    " t   t     t  t t   ".toCharArray,
    " t     t t          ".toCharArray,
    "         t  tt   t t".toCharArray,
    " tt    t            ".toCharArray,
    "     tt   t t t t  t".toCharArray,
    "  t     t      t t  ".toCharArray
  )
  def addConstraints(table: Table): Unit = {
    add(table.edgeConstraints)
    add(table.tentConstraints)
    add(table.colSumConstraints)
    add(table.rowSumConstraints)
  }

  val table: Table = Table(inputTable, rowSums, colSums)
  addConstraints(table)
  search {
    binaryFirstFail(table.allTrees)
  } onSolution {
    println(s"Solution found:\n$table")
  }

  println(start())
}
