name := "tents-and-trees"

version := "0.1"

scalaVersion := "2.12.13"

resolvers += Resolver.url("typesafe", url("https://repo.typesafe.com/typesafe/releases/"))

resolvers += ("Oscar Releases" at "http://artifactory.info.ucl.ac.be/artifactory/libs-release-local/").withAllowInsecureProtocol(true)

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.3.0"
libraryDependencies += "org.jfree" % "jfreechart" % "1.5.2"
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"

libraryDependencies += "oscar" %% "oscar-cp" % "4.0.0" withSources()
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

libraryDependencies += "com.lihaoyi" %% "upickle" % "0.9.5"
