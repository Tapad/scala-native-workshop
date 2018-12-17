inThisBuild {
  Seq(
    scalaVersion := "2.11.12",
    nativeLinkStubs := true // Set to false or remove if you want to show stubs as linking errors
  )
}

lazy val root = project
  .in(new File("."))
  .aggregate(app, common, curl)

lazy val common = project
  .enablePlugins(ScalaNativePlugin)
  .settings(
    libraryDependencies ++= Seq(
      "biz.enef" %%% "slogging" % "0.6.1"
    )
  )

lazy val curl = project
  .enablePlugins(ScalaNativePlugin)

lazy val app = project
  .enablePlugins(ScalaNativePlugin)
  .settings(
    libraryDependencies ++= Seq(
      "org.rogach" %%% "scallop" % "3.1.5",
      "biz.enef" %%% "slogging" % "0.6.1"
    )
  )
  .dependsOn(common, curl)
