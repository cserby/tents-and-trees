package dev.cserby.tentsandtrees

import oscar.algo.search.SearchStatistics
import oscar.cp.{CPBoolVar, CPIntVar, CPModel, Constraint, add, binaryFirstFail, search, start}

object TentsAndTreesSolver {
  def solve(puzzle: TentsAndTreesPuzzle): (SearchStatistics, Seq[TentsAndTreesSolution]) = {
    new CPModel() {
      private val solutionsAccumulator: scala.collection.mutable.Buffer[TentsAndTreesSolution] = scala.collection.mutable.Buffer.empty
      private val table: Matrix[Option[CPIntVar]] = Matrix(puzzle.inputTable.map { row: Array[Char] =>
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
        solutionsAccumulator.append(
          TentsAndTreesSolution(Matrix[Option[Int]](table.contents.map{ row =>
            row.map{ cell =>
              cell.map(_.value)
            }
          })))
      }

      def solutions: (SearchStatistics, Seq[TentsAndTreesSolution]) = {
        (start(), solutionsAccumulator)
      }

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
            row <- table.getRow(puzzle.rowSums.length - 1).toSeq
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
            row <- table.getCol(puzzle.colSums.length - 1).toSeq
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
                if !(row == row1 && col == col1)
                if !(row1 == row && col1 == col + 1) //the tent itself
              } yield {
                //            println(s"If ($row, $col) = 1, there's no tent at ($row1, $col1)")
                (tree ?=== 1) ==> !tentAt(row1, col1)
              }) ++
                (for {
                  row1 <- row to row + 2
                  col1 <- col - 1 to col + 1
                  if !(row == row1 && col == col1)
                  if !(row1 == row + 1 && col1 == col) //the tent itself
                } yield {
                  //            println(s"If ($row, $col) = 2, there's no tent at ($row1, $col1)")
                  (tree ?=== 2) ==> !tentAt(row1, col1)
                }) ++
                (for {
                  row1 <- row - 1 to row + 1
                  col1 <- col - 2 to col
                  if !(row == row1 && col == col1)
                  if !(row1 == row && col1 == col - 1) //the tent itself
                } yield {
                  //            println(s"If ($row, $col) = 3, there's no tent at ($row1, $col1)")
                  (tree ?=== 3) ==> !tentAt(row1, col1)
                }) ++
                (for {
                  row1 <- row - 2 to row
                  col1 <- col - 1 to col + 1
                  if !(row == row1 && col == col1)
                  if !(row1 == row - 1 && col1 == col) //the tent itself
                } yield {
                  //            println(s"If ($row, $col) = 4, there's no tent at ($row1, $col1)")
                  (tree ?=== 4) ==> !tentAt(row1, col1)
                })
            }
          }
        }.flatten.map(_.constraintTrue)
      }

      import oscar.cp.CPIntVarOps
      private def rowSumConstraints: Seq[Constraint] =
        (0 until table.rows).map { row =>
          (0 until table.cols).map { col =>
            tentAt(row, col)
          }.foldRight[CPIntVar](CPIntVar(0)){_ + _} === puzzle.rowSums(row)
        }

      private def colSumConstraints: Seq[Constraint] =
        (0 until table.cols).map { col: Int =>
          (0 until table.rows).map { row: Int =>
            tentAt(row, col)
          }.foldLeft[CPIntVar](CPIntVar(0)){_ + _} === puzzle.colSums(col)
        }

    }.solutions
  }
}
