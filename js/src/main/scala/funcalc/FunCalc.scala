package funcalc

import outwatch.dom._
import outwatch.dom.dsl._
import monix.execution.Scheduler.Implicits.global

final case class Index(row: Int, col: Char)


object FunCalc {
  def main(args: Array[String]): Unit = {

    OutWatch.renderInto("#app", h1("Hello World")).unsafeRunSync()
  }
}
