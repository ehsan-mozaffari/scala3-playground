package codechallenge.serenity.simplesearch

import scala.io.Source
import java.io.File
import scala.util.{Using,Try}
import scala.util.Try
import scala.util.Using.Releasable.AutoCloseableIsReleasable
import scala.util.Using.Releasable
import codechallenge.serenity.simplesearch.Ternary


object FileUtils:

  case class MyFile[A](fileName: String, ternary: Ternary[A])

  def open(path: String): File = new File(path)

  def open(paths: List[String]): List[File] = paths.map(open)

  private given releasable : Releasable[Iterator[String]] = _ => ()

  extension (file: File)
    def read(bufferSize: Int = Source.DefaultBufSize): 
        Either[Throwable, Iterator[String]] = Using(Source.fromFile(file).getLines())(identity).toEither
  
end FileUtils
