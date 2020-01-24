scalaVersion := "2.13.1"

lazy val ver = new {
  val mongo = "3.12.1"
  val monix = "3.1.0"
  val scalatest = "3.0.8"
  val logback = "1.2.3"
  val scalaLogging = "3.9.2"
  val config = "1.4.0"
}

lazy val monix = Seq(
  "io.monix" %% "monix" % ver.monix
)

lazy val testing = Seq(
  "org.scalatest" %% "scalatest" % ver.scalatest
)

lazy val logging = Seq(
  "ch.qos.logback" % "logback-classic" % ver.logback,
  "com.typesafe.scala-logging" %% "scala-logging" % ver.scalaLogging
)

lazy val config = Seq(
  "com.typesafe" % "config" % ver.config
)

lazy val database = Seq(
  "org.mongodb" % "mongodb-driver" % ver.mongo
)

lazy val compilerOptions = Seq(
  "-unchecked",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-deprecation",
  "-encoding",
  "utf8",
  //"-Ypartial-unification"
)

lazy val settings = Seq(
  scalacOptions ++= compilerOptions,
  libraryDependencies ++= testing.map(_ % Test) ++ logging++ config++ monix ++ database,
  resolvers ++= Seq(
    "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )
)

lazy val root = (project in file("."))
  .settings(
    name := "MongoDB",
    version := "0.1",
    settings
  )
