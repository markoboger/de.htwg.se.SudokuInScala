name          := "Sudoku in Scala"
organization  := "de.htwg.se"
version       := "0.0.1"
scalaVersion  := "2.12.3"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")

resolvers += Resolver.jcenterRepo

libraryDependencies ++= {
  val scalaTestV       = "3.0.4"
  val scalaMockV       = "3.6.0"
  Seq(
    "org.scalactic" %% "scalactic" % scalaTestV,
    "org.scalatest" %% "scalatest"                   % scalaTestV       % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockV       % "test"
  )
}

libraryDependencies += "junit" % "junit" % "4.8" % "test"
