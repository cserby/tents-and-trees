package dev.cserby.tentsandtrees

import oscar.cp._
import oscar.cp.{CPIntVar, Constraint}

class Table(
             private val table: Array[Array[Option[CPIntVar]]],
             private val rowSums: Array[Int],
             private val colSums: Array[Int],
             private val rows: Int,
             private val cols: Int)(implicit cpStore: CPStore) {
  private def get(row: Int, col: Int): Option[CPIntVar] = {
    getRow(row).flatMap(_.lift(col).flatten)
  }

  private lazy val transpose: Table = new Table(table.transpose, rowSums, colSums, rows, cols)

  private def getRow(row: Int): Option[Array[Option[CPIntVar]]] = {
    table.lift(row)
  }

  private def getCol(col: Int): Option[Array[Option[CPIntVar]]] = {
    transpose.getRow(col)
  }

  def allTrees: Seq[CPIntVar] = {
    table.flatten.flatten.toSeq
  }

  def edgeConstraints: Seq[Constraint] = {
    getRow(0).map{ _.flatMap { _.map { _ !== 4 }}}.getOrElse(Array.empty) ++
    getRow(rows - 1).map{ _.flatMap { _.map { _ !== 2 }}}.getOrElse(Array.empty) ++
    getCol(0).map{ _.flatMap { _.map { _ !== 3 }}}.getOrElse(Array.empty) ++
    getCol(cols - 1).map{ _.flatMap { _.map { _ !== 1 }}}.getOrElse(Array.empty)
  }

  // || | | | | ||
  // || | | | | ||
  // || | |t| | ||
  // || | | | | ||
  // || | | | | ||
  private def tentAt(row: Int, col: Int): CPBoolVar = {
    CPBoolVar(get(row, col).isEmpty) && (
      get(row - 1, col).map{ _ ?=== 2}.getOrElse(CPBoolVar(false)) ||
      get(row, col - 1).map{ _ ?=== 1}.getOrElse(CPBoolVar(false)) ||
      get(row, col + 1).map{ _ ?=== 3}.getOrElse(CPBoolVar(false)) ||
      get(row + 1, col).map{ _ ?=== 4}.getOrElse(CPBoolVar(false))
    )
  }

  def tentConstraints: Seq[Constraint] = {
    // || | | | | ||
    // || | | | | ||
    // || | |t| | ||
    // || | | | | ||
    // || | | | | ||
    (0 until rows).flatMap{ row =>
      (0 until cols).flatMap{ col =>
        get(row, col).map { tree =>
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
    }.flatten.map(_ === 1)
  }

  def rowSumConstraints: Seq[Constraint] = {
    (0 until rows).map { row =>
      (0 until cols).map { col =>
        tentAt(row, col)
      }.foldRight[CPIntVar](CPIntVar(0)){_ + _} === rowSums(row)
    }
  }

  def colSumConstraints: Seq[Constraint] = {
    (0 until cols).map { col =>
      (0 until rows).map { row =>
        tentAt(row, col)
      }.foldRight[CPIntVar](CPIntVar(0)){_ + _} === colSums(col)
    }
  }

  override def toString: String = {
    table.map{ row =>
      row.map{
        case None => " "
        case Some(tree) => tree.value.toString
      }.mkString("|")
    }.mkString("\n")
  }
}

object Table {
  def apply(inputTable: InputTable, rowSums: Array[Int], colSums: Array[Int])(implicit cpStore: CPStore): Table = {
    new Table(
      inputTable.map { row: Array[Char] =>
        row.map {
          case 't' => Some(CPIntVar(1 to 4))
          case _ => None
        }
      }, rowSums, colSums, inputTable.length, inputTable.head.length)
  }
}
