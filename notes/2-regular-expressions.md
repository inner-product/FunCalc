# Regular Expressions

We're now going to implement a technique for _parsing_ text, know as _regular expressions_. We're then going to parse arithmetic expressions into the data structure we've just built and evaluate these expressions. When we've finished we will have built the machinery to take a `String` like "1 + 1" and end up with the value 2. This might not seem like much but we will have actually implemented a tiny programming language!

What does parsing mean? It means to break something into its component parts. We usually talk about parsing text, but we can also talk about parsing visual scenes or any other kind of structured input where that structure is not immediately apparent. For example, given the `String` "1 + 2" we might parse "1" and "2" as literals, and the entire expression as the addition of two literals.

What are regular expressions? They are a simple method of matching a sequence of symbols. In our case our symbols will be characters in a `String`. The simplicity of regular expressions makes them easy to analyse and implement, but limits what they can do. Nonetheless they are very useful. Regular expressions are supported directly by many languages, including Scala, but we're going to build our own very simple regular expression implementation.

For our purpose a regular expression is:

- a literal, which contains a `String` and means to match exactly that `String`;
- alternation, which contains two regular expressions and matches if either of the regular expressions it contains matches;
- sequencing, which contains two regular expressions and matches if the first regular expression matches followed by the second; or
- repetition, which contains a regular expression and matches if the regular expression it contains matches 0 or more times.

What strategy could you use to express this description in code? 


## Implementation

This is a bigger project than we've taken on before, so we have some scaffolding to support you. Open the file `shared/src/main/scala/regex/Regex.scala`. This has a skeleton that we will fill out.

Now run the tests. If you're using SBT run the following commands:

```
project root
testOnly regex.RegexSuite
```

In IntelliJ you will have to select the `jvm` folder, right-click and choose `Run ScalaTest in jvm` option. We're only interested in the errors that come from `RegexSuite`. (IntelliJ does not properly support tests when building code for both the JVM and Javascript, which is what we're doing.)

You should see a lot of test failures. We'll make progress by making the tests pass one-by-one.


## Result

Our starting point is to implement the `Result` type, which is returned by the `parse` method on `Regex`.

A `Result` is either:

- a `Success`, holding the `String` that was matched, and the remaining input (also a `String`); or
- a `Failure`, holding the `String` input that failed to match.

Implement this.


## Reification

Now we can turn to implement `Regex`. We're going to use an implementation strategy called _reification_. To reify means to make concrete something that is abstract. Concretely in our case this means to make method calls into data. Reification combines algebraic data types and structural recursion. This is how it works:

There are two kinds of methods on `Regex`, those that return a `Regex` and those that return something else. The former are _combinators_. They combine `Regexes` to create more complex instances. We can also consider `literal` and other constructors, on the companion object, in this category. The remaining methods run or evaluate a `Regex` and produce some other result. 

There are seven methods in total on `Regex` and its companion object. Write down which group, combinators or evaluators, each method falls into.

Every combinator will simply create and return an instance of a data structure. We say the method is reified, as we turn the call into data. This data structure will hold all the method parameters. This must include the `this` parameter, where it is available, that we don't usually consider as a method parameter.

In our evaluators we process the data structure we just created to produce some other result. We have a strategy we can use to make this easier.

Remember we defined a regular expression as:

- a literal, which contains a `String` and means to match exactly that `String`;
- alternation, which contains two regular expressions and matches if either of the regular expressions it contains matches;
- sequencing, which contains two regular expressions and matches if the first regular expression matches followed by the second; or
- repetition, which contains a regular expression and matches if the regular expression it contains matches 0 or more times.

What strategy can we use to implement this? Implement this data structure.

Now change the combinator implementations from `???` to constructing an instance of the data structure you just created. You might find it easier to just change one method---`literal` is the best choice---so you can get something working without making too many changes.

We're now going to implement `parse`. At this point we have a data structure to process. What strategy did we use to create the `Regex` data structure? What strategy should we use to transform it?

To implement `parse` we'll need to know about two methods on `String`:

1. The `startsWith` method of `String` returns `true` if the `String` starts with the given `String` parameter. E.g. `"ScalaBridge".startsWith("Scala")` is `true` while `"ScalaBridge".startsWith("Java")` is `false`.

2. The `drop(n)` method on `String` returns the rest of the a `String` without the first `n` characters. E.g. `"Ziggy Stardust".drop(6)` is `"Stardust"`

I suggest you proceed by implementing the case only for `literal`. Then implement `matches` in terms of `parse`, which will allow the tests to start producing useful output. You should then be able to see the tests for literal passing. Now implement cases one-by-one until all the tests pass.

When matching repetitions you will have to make a decision as to how many repetitions to match. The usual answer is to be _greedy_: match as many as possible.
