## sbt project compiled with Scala 3 and JDK 19

### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

To run the project run `sbt run` and chose between the different main of the application to run.

use `~ reStart` in sbt to hot reload the application. this is because the `sbt-resolver` plugin that automatically activates
on all the module. if you want to disable it on each module specifically you could add some config on that module

For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).
