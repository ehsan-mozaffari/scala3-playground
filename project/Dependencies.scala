import sbt._ // For understanding Dependencies object as an sbt file

object Dependencies {

  object ver {
    val catsCore = "2.8.0"
    val munit = "0.7.29"
  }

  object lib {


    object cats {
      val core: Seq[ModuleID] = Seq("org.typelevel" %% "cats-core" % ver.catsCore)
    }

    object test {
      val munit: Seq[ModuleID] = Seq("org.scalameta" %% "munit" % ver.munit).map(_ % Test) // map for the sake of learning
    }
  }
}
