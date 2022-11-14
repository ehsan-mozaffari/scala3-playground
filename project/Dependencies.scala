import sbt._ // For understanding Dependencies object as an sbt file

object Dependencies {

  private object ver {
    val catsCore = "2.8.0"
    val munit = "0.7.29"
    val tapir = "1.2.1"
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
      }
    }
  }
}
