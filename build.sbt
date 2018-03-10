name := "squill"

version := "1.0"

scalaVersion := "2.12.4"

licenses := Seq("Apache 2.0" -> url("http://www.opensource.org/licenses/Apache-2.0"))

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

libraryDependencies += "com.h2database" % "h2" % "1.0.60" % Test
