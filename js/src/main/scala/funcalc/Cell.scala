package funcalc

import outwatch.dom._
import outwatch.dom.dsl._
import org.scalajs.dom
import monix.reactive.Observable

/**
  * Represents the UI of a cell of a spreadsheet
  */
final case class Cell(index: Index, commands: Handler[Cell.Command]) {

  val state = commands.scan(Cell.State(false, "")){ (state, command) =>
    command match {
      case Cell.Selected(idx) =>
        if(idx == index) state.copy(selected = true)
        else state.copy(selected = false)

      case Cell.Expr(idx, expr) =>
        if(idx == index) state.copy(expr = expr)
        else state

      case Cell.Empty =>
        state
    }
  }

  val ui: Observable[VNode] =
    state.map { case Cell.State(selected, expr) =>
      if(selected) {
        input(
          `class` := "border-solid border-2 border-green-500 w-20 h-8",
          onKeyPress.map(
            evt =>
              if (evt.keyCode == 13) {
                val expr =
                  evt.target
                    .valueOf()
                    .asInstanceOf[dom.html.Input]
                    .value
                Cell.Expr(index, expr)
              } else Cell.Empty
          ) --> commands,
          placeholder := expr
        )
      } else {
          td(
            `class` := "w-20 h-8 border-solid border border-gray-600",
            onClick.map(_ => Cell.Selected(index)) --> commands,
            expr
          )
      }
    }
}
object Cell {
  sealed trait Command
  final case class Expr(index: Index, value: String) extends Command
  final case class Selected(index: Index) extends Command
  final case object Empty extends Command

  final case class State(selected: Boolean, expr: String)
}
