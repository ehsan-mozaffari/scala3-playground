import Dependencies._
logLevel := Level.Info
name := "scala3-playground"

val scala3Version = "3.2.1"

scalacOptions ++= Seq(
  "-J-target:jvm-19", // It is no preventing not to compile scala with other java version, it just shows the byte code java version is jdk 19
  "-J-release 19",
  "-J-enable-preview"
)

javacOptions ++= Seq(
  "--release 19",
  "--enable-preview"
)

javaOptions ++= Seq(
  "--enable-preview"
)


lazy val root = project
  .in(file("."))
  .settings(
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= lib.cats.core ++ lib.test.munit
  )
