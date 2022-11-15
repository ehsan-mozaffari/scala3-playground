import sbt._ // For understanding Dependencies object as an sbt file

object Dependencies {

  private object ver {
    val catsCore = "2.8.0"
    val munit = "0.7.29"
    val tapir = "1.2.1"
    val logback = "1.4.4"
    val scalaLogging = "3.9.5"
    val pureConfig = "0.17.2"
  }

  object lib {

    object cats {
      val core: Seq[ModuleID] = Seq("org.typelevel" %% "cats-core" % ver.catsCore)
    }

    object test {
      // map for the sake of learning
      val munit: Seq[ModuleID] = Seq("org.scalameta" %% "munit" % ver.munit).map(_ % Test)
    }

    object api {
      object tapir {
        // the core tapir endpoint api
        val core: Seq[ModuleID] = Seq("com.softwaremill.sttp.tapir" %% "tapir-core" % ver.tapir)
        // circe support for tapir. it has circe-core, circe-parser, circe-generic. No need for adding circe solely.
        val circe: Seq[ModuleID] = Seq("com.softwaremill.sttp.tapir" %% "tapir-json-circe" % ver.tapir)
        // add ZIO HTTP server with tapir compatibility for converting endpoints to zio routes
        val zioHttp: Seq[ModuleID] = Seq("com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % ver.tapir)
      }
    }

    object log {
      val logback = Seq("ch.qos.logback" % "logback-classic" % ver.logback)
      // a type safe scala logging lib that uses logback with slf4j
      val scalaLogging = Seq("com.typesafe.scala-logging" %% "scala-logging" % ver.scalaLogging)
    }

    object config {
      // A type safe scala configuration management supports .conf .json .properties
      // TODO: change it to this: val pureConfig = Seq("com.github.pureconfig" %% "pureconfig" % ver.pureConfig).map(_.cross(CrossVersion.for3Use2_13))
      val pureConfigCore = Seq("com.github.pureconfig" %% "pureconfig-core" % ver.pureConfig)
    }
  }
}
