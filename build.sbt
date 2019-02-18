import com.tapad.platform.sbt.homebrew.FormulaUtils
import Release._

inThisBuild {
  Seq(
    scalaVersion := "2.11.12",
    nativeLinkStubs := true, // Set to false or remove if you want to show stubs as linking errors
    organization := "com.tapad.workshop",
    publishArtifact in Test := false,
    version in ThisBuild ~= (_.replace('+', '-')),
    dynver in ThisBuild ~= (_.replace('+', '-')),
    publishMavenStyle := false,
    publishTo := Some(Resolver.defaultLocal)
  )
}

lazy val root = (project in file("."))
  .settings(
    name := "tws", // For "Tapad Workshop"
    publish := {},
    publishLocal := {}
  )
  .settings(Release.ReleaseSettings)
  .aggregate(app, common, curl, makefile, homebrew)

lazy val homebrew = project
  .settings(
    homebrewFormula := sourceDirectory.value / "main" / "ruby" / "tws.rb",
    crossVersion := Disabled(),
    addArtifact(Artifact("tws", "formulae", "rb"), homebrewFormulaRender)
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
    publish := (publish in Universal).value
  )
  .enablePlugins(UniversalPlugin, UniversalDeployPlugin)

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
      "biz.enef" %%% "slogging" % "0.6.1",
      "com.softwaremill.sttp" %%% "core" % "1.5.0"
    )
  )
  .settings(
    Compile / mainClass := Some("com.tapad.app.Main"),
    nativeCompileOptions += "-I/usr/local/opt/curl/include"
  )
  .settings( // Provide linking settings for linking from SBT
    nativeLinkStubs := true,
    nativeLinkingOptions += "-L/usr/local/opt/curl/lib")
  .dependsOn(common)
