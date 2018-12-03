inThisBuild {
  Seq(
    scalaVersion := "2.11.12",
    nativeLinkStubs := true // Set to false or remove if you want to show stubs as linking errors
  )
}

lazy val root = project
  .in(new File("."))
  .aggregate(app, common)

lazy val common = project
  .enablePlugins(ScalaNativePlugin)

lazy val app = project
  .enablePlugins(ScalaNativePlugin)
  .dependsOn(common)
