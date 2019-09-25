package funcalc

import cats.effect.IO
import outwatch.dom._
import outwatch.dom.dsl._
import monix.execution.Scheduler
import monix.reactive.Observable

/**
  * Represents the UI of a spreadsheet
  */
object Sheet {
  val rows = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
  val cols = List('A', 'B', 'C', 'D', 'E', 'F', 'G')
  val indices =
    for {
      r <- rows
      c <- cols
    } yield Index(r, c)
  val initialState =
    indices.foldLeft(Map.empty[Index,String]){ (exprs, idx) =>
      exprs + (idx -> "")
    }

  val commands: IO[Handler[Cell.Command]] = Handler.create(Cell.Empty)
  val state: IO[Observable[Map[Index, String]]] =
    commands.map{ c =>
      c.scan(initialState){ (state, command) =>
        println(s"Command $command")
        command match {
          case Cell.Expr(idx, expr) => state + (idx -> expr)
          case Cell.Selected(_) => state
          case Cell.Empty => state
        }
      }
    }

  def ui(implicit schedule: Scheduler): IO[VNode] =
    for {
      c <- commands
      s <- state
    } yield {
      s.subscribe() // Make state actually run
      val cells = indices.map(idx => (idx -> Cell(idx, c))).toMap

      table(
        `class` := "w-full",
        tr(
          `class` := "h-8",
          th(`class` := "w-8", ""),
          cols.map(col => th(`class` := "w-20", col.toString))
        ),
        rows.map { row =>
            tr(
              `class` := "h-8",
              td(row.toString),
              cols.map { col =>
                val idx = Index(row, col)
                cells(idx).ui
              }
            )
          }
        )
    }
}
