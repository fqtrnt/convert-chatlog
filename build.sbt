name := "chatlogs"
 
version := "1.0" 

scalaVersion := "2.9.1"

resolvers += "Local Maven Repository" at "file:///media/e/develop-tools/m2repos"

libraryDependencies ++= Seq(
        "org.scala-tools.testing" %% "specs" % "1.6.9" % "test",
        "junit" % "junit" % "4.8.1" % "test",
        "org.scalatest" %% "scalatest" % "1.6.1" % "test"
)



