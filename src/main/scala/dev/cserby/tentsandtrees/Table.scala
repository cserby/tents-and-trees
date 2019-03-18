package dev.cserby.tentsandtrees

import oscar.cp._
import oscar.cp.{CPIntVar, Constraint}

class Table(private val table: Array[Array[Option[CPIntVar]]], val rows: Int, val cols: Int) {
  def get(row: Int, col: Int): Option[CPIntVar] = {
    getRow(row).flatMap(_.lift(col).flatten)
  }

  def transpose: Table = {
    new Table(table.transpose, rows, cols)
  }


  def getRow(row: Int): Option[Array[Option[CPIntVar]]] = {
    table.lift(row)
  }

  def getCol(col: Int): Option[Array[Option[CPIntVar]]] = {
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

  def tentConstraints: Seq[Constraint] = {
//    Seq((get(0, 0).get ?=== 1) ==> (get(2, 2).get ?=== 4) === 1)
    // || | | | | ||
    // || | | | | ||
    // || | |t| | ||
    // || | | | | ||
    // || | | | | ||
    (0 until rows).flatMap{ row =>
      (0 until cols).flatMap{ col =>
        get(row, col).map { tree =>
          get(row - 2, col - 2).map{ tm2m2 =>
            Seq(
              (tree ?=== 3) ==> !(tm2m2 ?=== 2),
              (tree ?=== 4) ==> !(tm2m2 ?=== 1)
            )
          }.getOrElse(Seq.empty) ++
          get(row - 2, col - 1).map{ tm2m1 =>
            Seq(
              (tree ?=== 3) ==> !(tm2m1 ?=== 2),
              (tree ?=== 4) ==> !(tm2m1 ?=== 1),
              (tree ?=== 4) ==> !(tm2m1 ?=== 2)
            )
          }.getOrElse(Seq.empty) ++
          get(row - 2, col).map{ tm2m0 =>
            Seq(
              (tree ?=== 3) ==> !(tm2m0 ?=== 2),
              (tree ?=== 4) ==> !(tm2m0 ?=== 1),
              (tree ?=== 4) ==> !(tm2m0 ?=== 2),
              (tree ?=== 4) ==> !(tm2m0 ?=== 3)
            )
          }.getOrElse(Seq.empty) ++
          get(row - 2, col + 1).map{ tm2p1 =>
            Seq(
              (tree ?=== 1) ==> !(tm2p1 ?=== 2),
              (tree ?=== 4) ==> !(tm2p1 ?=== 2),
              (tree ?=== 4) ==> !(tm2p1 ?=== 3)
            )
          }.getOrElse(Seq.empty) ++
          get(row - 2, col + 2).map{ tm2p2 =>
            Seq(
              (tree ?=== 1) ==> !(tm2p2 ?=== 2),
              (tree ?=== 4) ==> !(tm2p2 ?=== 3)
            )
          }.getOrElse(Seq.empty)/* ++
          get(row - 1, col - 2).map{ tm1m2 =>
            Seq(
              (tree ?=== 3) ==> !(tm2m2 ?=== 2),
              (tree ?=== 4) ==> !(tm2m2 ?=== 1)
            )
          }.getOrElse(Seq.empty) ++
          get(row - 1, col - 1).map{ tm1m1 =>
            Seq(
              (tree ?=== 3) ==> !(tm2m1 ?=== 2),
              (tree ?=== 4) ==> !(tm2m1 ?=== 1),
              (tree ?=== 4) ==> !(tm2m1 ?=== 2)
            )
          }.getOrElse(Seq.empty) ++
          get(row - 1, col).map{ tm1m0 =>
            Seq(
              (tree ?=== 3) ==> !(tm2m0 ?=== 2),
              (tree ?=== 4) ==> !(tm2m0 ?=== 1),
              (tree ?=== 4) ==> !(tm2m0 ?=== 2),
              (tree ?=== 4) ==> !(tm2m0 ?=== 3)
            )
          }.getOrElse(Seq.empty) ++
          get(row - 1, col + 1).map{ tm1p1 =>
            Seq(
              (tree ?=== 1) ==> !(tm2p1 ?=== 2),
              (tree ?=== 4) ==> !(tm2p1 ?=== 2),
              (tree ?=== 4) ==> !(tm2p1 ?=== 3)
            )
          }.getOrElse(Seq.empty) ++
          get(row - 1, col + 2).map{ tm1p2 =>
            Seq(
              (tree ?=== 1) ==> !(tm2p2 ?=== 2),
              (tree ?=== 4) ==> !(tm2p2 ?=== 3)
            )
          }.getOrElse(Seq.empty)*/
        }
      }
    }.flatten.map(_ === 1)
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
  def apply(inputTable: InputTable)(implicit cpStore: CPStore): Table = {
    new Table(
      inputTable.map { row: Array[Char] =>
        row.map {
          case 't' => Some(CPIntVar(1 to 4))
          case _ => None
        }
      }, inputTable.length, inputTable.head.length)
  }
}
