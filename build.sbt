name := """playpostfix"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "com.typesafe.slick" %% "slick-codegen" % "3.1.1",
  "com.h2database" % "h2" % "1.4.187" ,
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.2.0",
  "joda-time" % "joda-time" % "2.9.4",
  "org.joda" % "joda-convert" % "1.7",
  "mysql" % "mysql-connector-java" % "5.1.36"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
