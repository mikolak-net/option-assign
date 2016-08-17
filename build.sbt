import sbt.Keys._


lazy val commonSettings = Seq(
  name := "option-assign",
  version := "1.0",
  scalaVersion := "2.11.8"
)


lazy val plugin = project
  .settings(commonSettings)
  .settings(
    name := "optionassign-scalac-plugin",
    exportJars := true
  )
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-compiler" % scalaVersion.value
    )
  )

lazy val tests = project
  .dependsOn(plugin)
  .settings(commonSettings)
  .settings(
    scalacOptions <+= (artifactPath in(plugin, Compile, packageBin)).map { file =>
      s"-Xplugin:${file.getAbsolutePath}"
    }/*,scalacOptions += "-Xshow-phases"*/ //uncomment this if you want to see the phase ordering
  )

lazy val root = (project in file("."))
  .aggregate(plugin, tests)
  .settings(name := "option-assign")