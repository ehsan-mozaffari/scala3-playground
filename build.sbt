import Dependencies._

name := "scala3-playground"

val scala3Version = "3.2.1"

lazy val root = project
  .in(file("."))
  .settings(

    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= lib.cats.core ++ lib.test.munit
  )
