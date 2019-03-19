name := "tents-and-trees"

version := "0.1"

scalaVersion := "2.12.8"

resolvers += Resolver.url("typesafe", url("http://repo.typesafe.com/typesafe/releases/"))

resolvers += "Oscar Releases" at "http://artifactory.info.ucl.ac.be/artifactory/libs-release-local/"

libraryDependencies += "oscar" %% "oscar-cp" % "4.0.0" withSources()
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"