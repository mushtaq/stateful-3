
ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.thoughtworks"
ThisBuild / organizationName := "ThoughtWorks"

lazy val root = (project in file("."))
  .settings(
    name := "stateful-3",
    libraryDependencies ++= Seq(
      Akka.`akka-stream-typed`,
      Libs.`ammonite`,
      Libs.`scala-async`,
      Libs.`scalatest` % Test
    )
  )

