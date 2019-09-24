import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}
import org.scalajs.core.tools.linker.StandardLinker.Config

organization := "com.inner-product"
name := "FunCalc"
version := "0.1.0"


scalaVersion in ThisBuild := "2.13.0"

lazy val commonSettings = Seq(

  resolvers += "jitpack" at "https://jitpack.io",

  libraryDependencies ++= Seq(
    "org.scalatest" %%% "scalatest" % "3.0.8" % Test,
  ),

  scalacOptions ++=
    "-encoding" :: "UTF-8" ::
    "-unchecked" ::
    "-deprecation" ::
    "-explaintypes" ::
    "-feature" ::
    "-language:_" ::
    "-Xlint" ::
    "-Xlint:adapted-args" ::
    "-Wextra-implicit" ::
    "-Xlint:infer-any" ::
    "-Wvalue-discard" ::
    "-Xlint:nullary-override" ::
    "-Xlint:nullary-unit" ::
    Nil
)

lazy val root =
  // select supported platforms
  crossProject(JSPlatform, JVMPlatform)
    .withoutSuffixFor(JVMPlatform)
    .in(file("."))
    .enablePlugins(ScalaJSBundlerPlugin)
    .settings(commonSettings)
    .jsSettings(
      libraryDependencies += "com.github.outwatch.outwatch" %%% "outwatch" % "a332851",

      // useYarn := true, // makes scalajs-bundler use yarn instead of npm
      requireJsDomEnv in Test := true,
      scalaJSUseMainModuleInitializer := true,
      scalaJSLinkerConfig ~= ((c: Config) => c.withModuleKind(ModuleKind.CommonJSModule)), // configure Scala.js to emit a JavaScript module instead of a top-level script

      // hot reloading configuration:
      // https://github.com/scalacenter/scalajs-bundler/issues/180
      addCommandAlias("dev", "; compile; fastOptJS::startWebpackDevServer; devwatch; fastOptJS::stopWebpackDevServer"),
      addCommandAlias("devwatch", "~; fastOptJS; copyFastOptJS"),

      version in webpack := "4.16.1",
      version in startWebpackDevServer := "3.1.4",
      webpackDevServerExtraArgs := Seq("--progress", "--color"),
      webpackDevServerPort := 8080,
      webpackConfigFile in fastOptJS := Some(baseDirectory.value / "webpack.config.dev.js"),

      webpackBundlingMode in fastOptJS := BundlingMode.LibraryOnly() // https://scalacenter.github.io/scalajs-bundler/cookbook.html#performance
    )

// scalacOptions += "-P:scalajs:sjsDefinedByDefault"

// when running the "dev" alias, after every fastOptJS compile all artifacts are copied into
// a folder which is served and watched by the webpack devserver.
// this is a workaround for: https://github.com/scalacenter/scalajs-bundler/issues/180
lazy val copyFastOptJS = TaskKey[Unit]("copyFastOptJS", "Copy javascript files to target directory")
(root.js / copyFastOptJS) := {
  val inDir = (root.js / Compile / fastOptJS / crossTarget).value
  // (crossTarget in (Compile, fastOptJS)).value
  val outDir = inDir / "dev"
    // (crossTarget in (Compile, fastOptJS)).value / "dev"
  val files = Seq("root-fastopt-loader.js", "root-fastopt.js") map { p => (inDir / p, outDir / p) }
  IO.copy(files, overwrite = true, preserveLastModified = true, preserveExecutable = true)
}
