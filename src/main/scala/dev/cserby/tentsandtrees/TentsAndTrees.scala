package dev.cserby.tentsandtrees

import oscar.cp.{CPIntVar, CPModel, Constraint, add, binaryFirstFail, search, start}

object TentsAndTrees extends CPModel with App {
  val inputTable: Array[Array[Char]] = Array(
    Array('t', ' ', ' '),
    Array(' ', ' ', ' '),
    Array(' ', ' ', 't')
  )

  val rowSums: Array[Int] = Array(0, 2, 0)
  val colSums: Array[Int] = Array(1, 0, 1)

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
