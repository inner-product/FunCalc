# Expressions

Our starting point is to implement a data structure that can represent arithmetic expressions such as `1 + 1` or `3 * 4 - 2`. Once this is complete we will implement an evaluator for this data structure: a method that, given an expression, runs that expression.

For our purposes, and expression is either:

- a literal number (which we'll choose to represent as a `Double`);
- an addition, which contains two sub-expressions;
- a subtraction, which contains two sub-expressions;
- a multiplication, which contains two sub-expressions; or
- a division, which (surprise!) contains two sub-expressions.

Which strategy could you use to implement this? Implement it.


Let's suppose you gave the name `Expression` to the type you were asked to implement above. Now we're going to implement a method `eval`, that given an `Expression` will run that expression, returning a `Double`. So if we represented `1 + 1` as an `Expression`, calling `eval` would produce `2`. Which strategy could we use? Do it.

Now implement a method `print` that given an `Expression` returns a `String` representation of that `Expression` using the usual notation for arithmetic (e.g. `1 + 1`). Which strategy could we use? Do it.
