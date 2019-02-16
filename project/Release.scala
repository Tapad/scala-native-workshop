import sbtrelease.ReleasePlugin.autoImport.{ReleaseStep, releaseProcess, releaseStepCommandAndRemaining, _}
import sbtrelease.ReleaseStateTransformations.{checkSnapshotDependencies, pushChanges, runClean, tagRelease, _}
import sbtrelease._
import sbt._
import sbt.Keys._

object Release {

  val ReleaseSettings = Seq(
    releaseVersion := { // Bump version on release
      val bumper = releaseVersionBump.value
      ver => Version(ver).map(_.bump(bumper)).map(_.withoutQualifier.string).getOrElse(versionFormatError(ver))
    },
    releaseProcess := Seq[ReleaseStep](
        checkSnapshotDependencies,
        inquireVersions,
        setReleaseVersion(_._1),
        tagRelease,
        pushChanges,
        runClean, //do sbt clean to force rebuilding all existing artifacts to ensure reapplying the version build setting
        releaseStepCommandAndRemaining("+publish")
      ),
    releaseIgnoreUntrackedFiles := true
  )

  // modified from sbt-release to not write to version.sbt
  private def setReleaseVersion(selectVersion: Versions => String): ReleaseStep = { st: State => {
    val vs = st.get(ReleaseKeys.versions).getOrElse(sys.error("No versions are set?!"))
    val selected = selectVersion(vs)

    st.log.info("Setting version to '%s'." format selected)

    reapply(Seq(
      version in ThisBuild := selected,
      version := selected
    ), st)
  } }
}
