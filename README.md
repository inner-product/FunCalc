# FunCalc

This case study builds a spreadsheet that runs in the browser. It consists of the following components:

- building a representation for expressions
- building a small regular expression library, to parse expressions
- wiring everything together to complete the spreadsheet.

It's named after VisiCalc, the first spreadsheet program, and inspired by a [similar case study][your-own-excel] by Tomas Petricek.



## Tutorial

The text in the `notes` directory guides you through completion of the project. It assumes you're taking one of our training courses and so understand terms like "algebraic data types".


## Prerequisites

The first two components of the project don't require any special setup, beyond installing the JVM for Scala. The spreadsheet itself has additional prerequisites:

 - [Node.js and NPM](https://nodejs.org/en/download/)


## Development

### Everything but the Spreadsheet

Develop code as usual. When you compile code you might see errors coming from the Scala.js components. To avoid this you can switch to just the JVM components. In `sbt` use the command `project rootJVM`.


### The Spreadsheet

Switching to the Javascript part of the project might help: `project rootJS` in `sbt`.

### Create a module
in sbt shell: `fastOptJS::webpack` or `fullOptJS::webpack`

### Working in dev mode
In sbt shell, run `dev`. Then open `http://localhost:8080/` in your browser.

This sbt-task will start webpack dev server, compile your code each time it changes
and auto-reload the page.  
webpack dev server will close automatically when you stop the `dev` task
(e.g by hitting `Enter` in the sbt shell while you are in `dev` watch mode).

If you exited ungracefully and your webpack dev server is still open (check with `ps -aef | grep -v grep | grep webpack`), you can close it by running `fastOptJS::stopWebpackDevServer` in sbt.


[your-own-excel]: http://tomasp.net/blog/2018/write-your-own-excel/
