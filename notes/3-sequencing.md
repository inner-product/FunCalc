# Sequencing Regular Expressions

At this point you have implemented the regular expression library and yo have used it to parse simple arithmetic expressions. Using ht elibrary is a bit tedious as, although we have methods to combine regular expressions we can't preserve that structure in the results we get. For example, if we parse a sequence of regular expressions we might like to extract the individual components we parsed. Concretely, if we parsed `someRegex.andThen(anotherRegex)` we might like the result to be a tuple of two `Strings` instead of the entire parsed `String`. We might also like to transform components of the regular expression as we parse them. For example, if we parse a number we might want to transform the result into a `Double` rather than just dealing with `String` everywhere.

The result of this change is we no longer can say ahead of time what type will be returned from a `Regex`. What does this suggest we need to add to `Regex`? We also need to a matching change to `Result`, now that a `Result` no longer stores only a `String`.

Checkout the `sequencing` branch. This contains an implementation of `Regex` with additional methods added. As before you should implement these methods. You can use the same implementation strategy, reification, to implement these methods. There are additional tests you can use to guide your implementation.
