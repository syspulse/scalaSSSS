import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.gladow",
      scalaVersion := "2.13.4",
      crossScalaVersions := Seq("2.12.1", "2.11.8"),
      version := "0.2.0-SNAPSHOT"
    )),
    name := "scalaSSSS",
    libraryDependencies ++= Seq(scalaCheck % Test) ++ Seq(
      "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.2"
    )
  )
