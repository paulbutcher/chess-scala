object Chess extends App {

  case class Piece(name: Char, takes: ((Int, Int)) => Boolean, pos: (Int, Int)) {
    override def toString = s"$name: $pos"

    def allows(p: (Int, Int)) = p != pos && !takes((p._1 - pos._1).abs, (p._2 - pos._2).abs)
  }

  abstract class PieceFactory(name: Char, takes: ((Int, Int)) => Boolean) {
    def apply(pos: (Int, Int)) = new Piece(name, takes, pos)
  }

  object K extends PieceFactory('K', { d => d._1 <= 1 && d._2 <= 1 })
  object Q extends PieceFactory('Q', { d => d._1 == 0 || d._2 == 0 || d._1 == d._2 })
  object R extends PieceFactory('R', { d => d._1 == 0 || d._2 == 0 })
  object B extends PieceFactory('B', { d => d._1 == d._2 })
  object N extends PieceFactory('N', { d => (d._1 == 1 && d._2 == 2) || (d._1 == 2 && d._2 == 1) })

  class Board(rows: Int, columns: Int) {

    def isAllowed(candidate: Piece, solution: Set[Piece]) =
      solution forall { piece => (piece allows candidate.pos) && (candidate allows piece.pos) }

    def getSolutions(pieces: List[PieceFactory]): Set[Set[Piece]] = pieces match {
      case Nil => Set(Set())
      case _ => for {
          solution <- getSolutions(pieces.tail)
          x <- 0 until rows
          y <- 0 until columns
          candidate = pieces.head (x, y)
          if isAllowed(candidate, solution)
        } yield solution + candidate
    }
  }

  // val solutions = new Board(3, 3).getSolutions(List(K, K, R))
  // val solutions = new Board(4, 4).getSolutions(List(R, R, N, N, N, N))
  val solutions = new Board(6, 9).getSolutions(List(K, K, N, R, B, Q))
  println(s"Count: ${solutions.size}")
  // println(solutions mkString "\n")
}