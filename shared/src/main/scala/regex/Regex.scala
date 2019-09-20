package regex

sealed trait Regex {

  /**
    * Match this regular expression zero or more times
    */
  def repeat: Regex =
    ???

  /**
    * Match this regular expression or the given regular expression
    *
    * For example, `Regex.literal("Scala").or(Regex.literal("Stairs"))` with
    * match either "Scala" or "Stairs"
    */
  def or(that: Regex): Regex =
    ???

  /**
    * Match this regular expression and then the given regular expression on the
    * remainder.
    *
    * For example, `Regex.literal("Ziggy").andThen(Regex.literal("Stardust")`
    * will match "ZiggyStardust"
    */
  def andThen(that: Regex): Regex =
    ???


  /**
   * Parse the given `String` using this regular expression.
   *
   * For example,
   *
   *   Regex.literal("Avril 14th").parse("Avril 14th Murcof & Wagner")
   *
   *   should succeed, matching "Avril 14th" and a remainder of " Murcof &
   *   Wagner"
   *
   *   Regex.literal("Cichli").parse("Oneohtrix")
   *
   *   should fail.
   */
  def parse(input: String): Result =
    ???

  /**
   * True is this regular expression successfully parses the given `String`,
   * otherwise false.
   */
  def matches(input: String): Boolean =
    ???
}
object Regex {

  /**
    * Construct a regular expression that matches the provided `String`.
    *
    * For example, `Regex.literal("Scala")` will match "Scala"
    */
  def literal(value: String): Regex =
    ???

  /**
    * Creates a regular expression that matches one of the characters in the
    * given `String`
    *
    * For example, `Regex.oneOf("0123456789")` with match "0", "1", and so on.
    */
  def oneOf(chars: String): Regex =
    if (chars.isEmpty())
      throw new Exception("You must supply at least one element to Regex.oneOf")
    else {
      val first = chars.head
      val rest = chars.tail
      rest.toList.foldLeft(Regex.literal(first.toString)) { (accum, char) =>
        accum.or(literal(char.toString))
      }
    }

}
