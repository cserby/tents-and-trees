package dev.cserby.tentsandtrees

import oscar.cp.{CPIntVar, CPModel, Constraint, add, binaryFirstFail, search, start}

object TentsAndTrees extends CPModel with App {
  val inputTable: Array[Array[Char]] = Array(
    Array('t', ' ', ' '),
    Array(' ', ' ', ' '),
    Array(' ', ' ', 't')
  )

  def addConstraints(table: Table): Unit = {
    add(table.edgeConstraints)
    add(table.tentConstraints)
    //add(table.columnSumConstraints)
    //add(table.rowSumConstraints)
  }

  val table: Table = Table(inputTable)
  addConstraints(table)
  search {
    binaryFirstFail(table.allTrees)
  } onSolution {
    println(s"Solution found:\n$table")
  }

  println(start())
}
