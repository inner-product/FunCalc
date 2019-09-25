package funcalc

import cats.effect.IO
import outwatch.dom._
import outwatch.dom.dsl._
import monix.execution.Scheduler

object Render {

  val selected: IO[Handler[Index]] =
    Handler.create(Index(1, 'A'))

  // def isSelected(row: Int, col: Char): Boolean =
  //   selected.map { case (r, c) => r == row && c == col }.getOrElse(false)

  def render(expressions: Handler[Map[Index,String]])(implicit schedule: Scheduler): IO[VNode] =
    selected.map{ selected =>
      div(
        h1(`class` := "font-sans text-3xl p-6", "FunCalc"),
        Sheet.ui
      )
    }
}
