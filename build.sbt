import com.tapad.platform.sbt.homebrew.FormulaUtils
import sbt.Keys.organization
import Repositories._
import Release._

inThisBuild {
  Seq(
    scalaVersion := "2.11.12",
    nativeLinkStubs := true, // Set to false or remove if you want to show stubs as linking errors
    organization := "com.tapad.workshop",
    publishTo := {
      if (version.value.endsWith("SNAPSHOT")) Some(TapadSnapshots) else Some(TapadReleases)
    },
    publishArtifact in Test := false
  )
}

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
resolvers += "Tapad Aggregate" at "https://nexus.tapad.com/repository/aggregate"

lazy val root = (project in file("."))
  .settings(ReleaseSettings)
  .aggregate(app, common, curl)

lazy val homebrew = project
  .settings(
    packageName in Universal := s"jgows-${version.value}",
    name in Universal := "jgows",
    topLevelDirectory := None,
    mappings in Universal += (resourceDirectory in Compile).value / "Makefile" -> "Makefile",
    Compile / packageBin := (Universal / packageBin).value,
    publishLocal := (publishLocal in Universal).value,
    publish := (publish in Universal).value,
    homebrewTapRepository := "git@github.com:jgogstad/homebrew-testtap.git",
    homebrewTapRepositoryPath := "formulas/jgows.rb",
    homebrewFormula := sourceDirectory.value / "main" / "ruby" / "jgows.rb",
    homebrewFormulaChecksum := FormulaUtils.sha256((Compile / packageBin).value)
  )
  .enablePlugins(UniversalPlugin, UniversalDeployPlugin, HomebrewPlugin)

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
