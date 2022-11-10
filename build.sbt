import Dependencies._
logLevel := Level.Debug
name := "scala3-playground"

val scala3Version = "3.2.1"

scalacOptions ++= Seq(
  "-J-target:jvm-19",
  "-J-release 19",
  "-J-enable-preview 19"
)

lazy val root = project
  .in(file("."))
  .settings(
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= lib.cats.core ++ lib.test.munit
  )
