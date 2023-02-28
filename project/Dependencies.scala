import sbt._ // For understanding Dependencies object as an sbt file

object Dependencies {

  private object ver {
    val catsCore     = "2.9.0"
    val catsEffect   = "3.4.8"
    val zio          = "2.0.9"
    val munit        = "0.7.29"
    val tapir        = "1.2.9"
    val logback      = "1.4.5"
    val scalaLogging = "3.9.5"
    val pureConfig   = "0.17.2"
    val http4s       = "1.0.0-M37"
  }

  object lib {

    object cats {
      val core: Seq[ModuleID] = Seq("org.typelevel" %% "cats-core" % ver.catsCore)

      object effect {
        // Cats effect core withSources() to download source without IDE plugin and same for withJavadoc()
        val core: Seq[ModuleID] = Seq(
          "org.typelevel" %% "cats-effect" % ver.catsEffect withSources () withJavadoc ()
        )
      }
    }

    object zio {
      val core:      Seq[ModuleID] = Seq("dev.zio" %% "zio" % ver.zio withSources () withJavadoc ())
      val test:      Seq[ModuleID] = Seq("dev.zio" %% "zio-test" % ver.zio withSources () withJavadoc ())
      val testSbt:   Seq[ModuleID] = Seq(
        "dev.zio" %% "zio-test-sbt" % ver.zio withSources () withJavadoc ()
      )
      val streams:   Seq[ModuleID] = Seq(
        "dev.zio" %% "zio-streams" % ver.zio withSources () withJavadoc ()
      )
      val testJUnit: Seq[ModuleID] = Seq(
        "dev.zio" %% "zio-test-junit" % ver.zio withSources () withJavadoc ()
      )
    }

    object test {
      // map for the sake of learning
      val munit: Seq[ModuleID] = Seq("org.scalameta" %% "munit" % ver.munit).map(_ % Test)
    }

    object api {
      object tapir {
        // the core tapir endpoint api
        val core:    Seq[ModuleID] = Seq("com.softwaremill.sttp.tapir" %% "tapir-core" % ver.tapir)
        // circe support for tapir. it has circe-core, circe-parser, circe-generic. No need for adding circe solely.
        val circe:   Seq[ModuleID] = Seq(
          "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % ver.tapir
        )
        // add ZIO HTTP server with tapir compatibility for converting endpoints to zio routes
        val zioHttp: Seq[ModuleID] = Seq(
          "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % ver.tapir
        )
      }

      object http4s {
        val dsl:         Seq[ModuleID] = Seq("org.http4s" %% "http4s-dsl" % ver.http4s)
        val emberServer: Seq[ModuleID] = Seq("org.http4s" %% "http4s-ember-server" % ver.http4s)
        val emberClient: Seq[ModuleID] = Seq("org.http4s" %% "http4s-ember-client" % ver.http4s)
        val core:        Seq[ModuleID] = dsl ++ emberServer ++ emberClient
      }
    }

    object log {
      val logback      = Seq("ch.qos.logback" % "logback-classic" % ver.logback)
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
