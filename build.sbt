import Dependencies._
logLevel := Level.Info

name := "scala3-playground"

lazy val scala3Version = "3.2.1"
scalaVersion := scala3Version

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
    version := "0.1.0",
    libraryDependencies ++= Nil ++
      lib.cats.core ++
      lib.cats.effect.core ++
      lib.test.munit ++
      lib.api.tapir.core ++
      lib.api.tapir.circe ++
      lib.api.tapir.zioHttp ++
      lib.log.logback ++
      lib.log.scalaLogging ++
      lib.config.pureConfigCore ++
      Nil
  )
