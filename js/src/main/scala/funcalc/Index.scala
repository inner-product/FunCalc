package funcalc

final case class Index(row: Int, col: Char) {
  override def toString(): String =
    s"${col}${row}"
}
