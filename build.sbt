name := "tinga"

organization := "com.vidtecci"

version := "0.1.0"

scalaVersion := "2.11.6"

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

libraryDependencies ++= Seq(
  "org.scalatestplus" %% "play" % "1.1.0" % "test"
)

initialCommands := "import tinga._"
