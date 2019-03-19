package dev.cserby.tentsandtrees

import org.scalatest.{FunSpec, Matchers}

class AcceptanceTests extends FunSpec with Matchers {
  describe("Tents and Trees solver") {
    it("3x3 easy") {
      println(TentsAndTreesPuzzle(
        Array(
          Array('t', ' ', ' '),
          Array(' ', ' ', ' '),
          Array(' ', ' ', 't')
        ),
        Array(0, 2, 0),
        Array(1, 0, 1)
      ).solve)
    }

    it("8x8 easy") {
      println(TentsAndTreesPuzzle(
        Array(
          " t      ",
          "   t  t ",
          "   t   t",
          "        ",
          "    t t ",
          "t  t   t",
          "      t ",
          "t       "
        ).map(_.toCharArray),
        Array(1, 3, 0, 1, 3, 1, 2, 1),
        Array(1, 2, 1, 2, 0, 3, 0, 3)
      ).solve)
    }

    it("20x20 hard") {
      println(TentsAndTreesPuzzle(
        Array(
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
        ).map(_.toCharArray),
        Array(6, 4, 3, 6, 2, 6, 1, 5, 3, 4, 5, 3, 3, 4, 3, 4, 2, 7, 1, 8),
        Array(4, 5, 2, 6, 2, 5, 4, 5, 2, 5, 4, 3, 5, 4, 5, 3, 3, 4, 3, 6)
      ).solve)
    }
  }
}
