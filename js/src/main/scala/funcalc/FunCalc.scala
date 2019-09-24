package funcalc

import cats.effect.IO
import outwatch.dom._
import monix.execution.Scheduler.Implicits.global

object FunCalc {

  val expressions: IO[Handler[Map[Index, String]]] =
    Handler.create(Map.empty)

  def main(args: Array[String]): Unit = {

    val program: IO[Unit] =
      for {
        expr <- expressions
        rdr  <- Render.render(expr)
        _    <- OutWatch.renderInto("#app", rdr)
      } yield ()

    program.unsafeRunSync()
  }
}
