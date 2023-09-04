package codechallenge.serenity.simplesearch

import codechallenge.serenity.simplesearch.FileUtils.*


case class  InMemoryCache(filePaths: List[String]) {

    lazy val myCachedFiles = FileUtils.open(filePaths).map{ openedFile =>
        val readFile = openedFile.read()
        val s : Either[Throwable, MyFile[String]]= readFile.map(words => MyFile(openedFile.getName(), Ternary(words)))

    }
}
