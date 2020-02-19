import sbt._

object Libs {
  val `scalatest`                = "org.scalatest"          %% "scalatest"                % "3.1.0"
  val `scala-async`              = "org.scala-lang.modules" %% "scala-async"              % "0.10.0"
  val `enumeratum`               = "com.beachape"           %% "enumeratum"               % "1.5.15"
  val `ammonite`                 = "com.lihaoyi"            % "ammonite"                  % "2.0.1" cross CrossVersion.full
  val `borer-core`        = "io.bullet" %% "borer-core" % "1.4.0"
  val `borer-derivation`  = "io.bullet" %% "borer-derivation" % "1.4.0"
  val `borer-compat-akka` = "io.bullet" %% "borer-compat-akka" % "1.4.0"

}

object Akka {
  val Version                 = "2.6.3"
  val `akka-stream-typed`     = "com.typesafe.akka" %% "akka-stream-typed" % Version
  val `akka-distributed-data` = "com.typesafe.akka" %% "akka-distributed-data" % Version
  val `akka-cluster-typed`    = "com.typesafe.akka" %% "akka-cluster-typed" % Version
  val `akka-slf4j`            = "com.typesafe.akka" %% "akka-slf4j" % Version
}

object AkkaHttp {
  val Version     = "10.1.11"
  val `akka-http` = "com.typesafe.akka" %% "akka-http" % Version
}
