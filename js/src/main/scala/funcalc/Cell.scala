package funcalc

import outwatch.dom._
import outwatch.dom.dsl._
import org.scalajs.dom
import monix.reactive.Observable

/**
  * Represents the UI of a cell of a spreadsheet
  */
final case class Cell(index: Index, commands: Handler[Cell.Command]) {

  val state = commands.scan(Cell.State.empty){ (state, command) =>
    command match {
      case Cell.Selected(idx) =>
        if(idx == index) state.copy(selected = true)
        else state.copy(selected = false)

      case Cell.Expr(idx, expr) =>
        if(idx == index) state.copy(selected = false, expr = expr)
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
          onChange.map{evt =>
            Cell.Expr(index, evt.target.asInstanceOf[dom.html.Input].value),
          } --> commands,
          // I don't understand why a cast is needed here. AFAIK dom.Element IS dom.raw.HTMLElement
          // Anyway, this doesn't seem to work
          onDomUpdate.foreach{elt => elt.asInstanceOf[dom.html.Element].focus()},
          value := expr
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

  final case class State(
    /* True if this cell is currently selected. False otherwise. */
    selected: Boolean,
    /* The expression stored in this cell. */
    expr: String
  )
  object State {
    val empty = State(false, "")
  }
}
