package dev.cserby.tentsandtrees

import oscar.algo.search.SearchStatistics
import oscar.cp._
import oscar.cp.{CPIntVar, Constraint}

case class TentsAndTreesPuzzle(
             inputTable: Array[Array[Char]],
             rowSums: Array[Int],
             colSums: Array[Int]) extends CPModel {

  require(inputTable.length > 0)
  require(inputTable.forall(_.length > 0))
  require(inputTable.forall(_.length == inputTable.head.length))
  require(rowSums.length == inputTable.length)
  require(colSums.length == inputTable.head.length)

  private val table: Matrix[Option[CPIntVar]] = Matrix(inputTable.map { row: Array[Char] =>
    row.map {
      case 't' => Some(CPIntVar(1 to 4))
      case _ => None
    }
  })

  add(edgeConstraints)
  add(tentConstraints)
  add(colSumConstraints)
  add(rowSumConstraints)

  search {
    binaryFirstFail(allTrees)
  } onSolution {
    println(s"Solution found:\n$this")
  }

  def solve: SearchStatistics = start()

  private def allTrees: Seq[CPIntVar] = {
    table.contents.flatten.flatten.toSeq
  }

  private def edgeConstraints: Seq[Constraint] = {
    (for {
      row <- table.getRow(0).toSeq
      treeO <- row
      tree <- treeO
    } yield {
      tree !== 4
    }) ++
    (for {
      row <- table.getRow(rowSums.length - 1).toSeq
      treeO <- row
      tree <- treeO
    } yield {
      tree !== 2
    }) ++
    (for {
      row <- table.getCol(0).toSeq
      treeO <- row
      tree <- treeO
    } yield {
      tree !== 3
    }) ++
    (for {
      row <- table.getCol(colSums.length - 1).toSeq
      treeO <- row
      tree <- treeO
    } yield {
      tree !== 1
    })
  }

  // || | | | | ||
  // || | | | | ||
  // || | |t| | ||
  // || | | | | ||
  // || | | | | ||
  private def tentAt(row: Int, col: Int): CPBoolVar = {
    CPBoolVar(table.get(row, col).flatten.isEmpty) && (
      table.get(row - 1, col).flatten.map{ _ ?=== 2}.getOrElse(CPBoolVar(b = false)) ||
        table.get(row, col - 1).flatten.map{ _ ?=== 1}.getOrElse(CPBoolVar(b = false)) ||
        table.get(row, col + 1).flatten.map{ _ ?=== 3}.getOrElse(CPBoolVar(b = false)) ||
        table.get(row + 1, col).flatten.map{ _ ?=== 4}.getOrElse(CPBoolVar(b = false))
    )
  }

  private def tentConstraints: Seq[Constraint] = {
    // || | | | | ||
    // || | | | | ||
    // || | |t| | ||
    // || | | | | ||
    // || | | | | ||
    (0 until table.rows).flatMap{ row =>
      (0 until table.cols).flatMap{ col =>
        table.get(row, col).flatten.map { tree =>
          (for {
            row1 <- row - 1 to row + 1
            col1 <- col to col + 2
            if row != row1 && col != col1
          } yield {
            (tree ?=== 1) ==> !tentAt(row1, col1)
          }) ++
          (for {
            row1 <- row to row + 2
            col1 <- col - 1 to col + 1
            if row != row1 && col != col1
          } yield {
            (tree ?=== 2) ==> !tentAt(row1, col1)
          }) ++
          (for {
            row1 <- row - 1 to row + 1
            col1 <- col - 2 to col
            if row != row1 && col != col1
          } yield {
            (tree ?=== 3) ==> !tentAt(row1, col1)
          }) ++
          (for {
            row1 <- row - 2 to row
            col1 <- col - 1 to col + 1
            if row != row1 && col != col1
          } yield {
            (tree ?=== 4) ==> !tentAt(row1, col1)
          })
        }
      }
    }.flatten.map(_.constraintTrue)
  }

  private def rowSumConstraints: Seq[Constraint] = {
    (0 until table.rows).map { row =>
      (0 until table.cols).map { col =>
        tentAt(row, col)
      }.foldRight[CPIntVar](CPIntVar(0)){_ + _} === rowSums(row)
    }
  }

  private def colSumConstraints: Seq[Constraint] = {
    (0 until table.cols).map { col =>
      (0 until table.rows).map { row =>
        tentAt(row, col)
      }.foldRight[CPIntVar](CPIntVar(0)){_ + _} === colSums(col)
    }
  }

  override def toString: String = {
    table.contents.map{ row =>
      row.map{
        case None => " "
        case Some(tree) => tree.value.toString
      }.mkString("|")
    }.mkString("\n")
  }
}
