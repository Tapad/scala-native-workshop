import com.tapad.platform.sbt.homebrew.FormulaUtils
import sbt.Keys.organization

inThisBuild {
  Seq(
    scalaVersion := "2.11.12",
    nativeLinkStubs := true, // Set to false or remove if you want to show stubs as linking errors
    organization := "com.tapad.workshop",
    publishArtifact in Test := false,
    version in ThisBuild ~= (_.replace('+', '-')),
    dynver in ThisBuild ~= (_.replace('+', '-'))
  )
}

lazy val root = (project in file("."))
  .settings(
    publish := {},
    publishLocal := {}
  )
  .settings(Release.ReleaseSettings)
  .aggregate(app, common, curl, makefile, homebrew)

lazy val homebrew = project
  .settings(
    homebrewFormula := sourceDirectory.value / "main" / "ruby" / "tws.rb",
    publishMavenStyle := false,
    crossVersion := Disabled(),
    addArtifact(Artifact("homebrew", "formulae", "rb"), homebrewFormulaRender)
  )
  .dependsOn(makefile, app)
  .enablePlugins(HomebrewPlugin)

lazy val makefile = project
  .settings(
    packageName in Universal := s"tws-${version.value}",
    name in Universal := "tws",
    topLevelDirectory := None,
    mappings in Universal += ((Compile / resourceDirectory).value / "Makefile") -> "Makefile",
    Compile / packageBin := (Universal / packageBin).value,
    publishLocal := (publishLocal in Universal).value,
    publish := (publishLocal in Universal).value
  )
  .enablePlugins(UniversalPlugin, UniversalDeployPlugin)

lazy val common = project
  .enablePlugins(ScalaNativePlugin)
  .settings(
    libraryDependencies ++= Seq(
      "biz.enef" %%% "slogging" % "0.6.1"
    ),
    publish := (publishLocal in Compile).value
  )

lazy val curl = project
    .settings(
      publish := (publishLocal in Compile).value)
  .enablePlugins(ScalaNativePlugin)

lazy val app = project
  .enablePlugins(ScalaNativePlugin)
  .settings(
    libraryDependencies ++= Seq(
      "org.rogach" %%% "scallop" % "3.1.5",
      "biz.enef" %%% "slogging" % "0.6.1",
      "com.softwaremill.sttp" %%% "core" % "1.5.0"
    ),
    publish := (publishLocal in Compile).value
  )
  .settings(
    Compile / mainClass := Some("com.tapad.app.Main"),
    nativeCompileOptions += "-I/usr/local/opt/curl/include"
  )
  .settings( // Provide linking settings for linking from SBT
    nativeLinkStubs := true,
    nativeLinkingOptions += "-L/usr/local/opt/curl/lib")
  .dependsOn(common)