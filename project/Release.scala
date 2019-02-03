import sbt.Keys.sbtPlugin
import sbtrelease.ReleasePlugin.autoImport.{ReleaseStep, releaseProcess, releaseStepCommandAndRemaining}
import sbtrelease.ReleaseStateTransformations.{checkSnapshotDependencies, pushChanges, runClean, tagRelease}
import sbtrelease.ReleasePlugin.autoImport.ReleaseKeys._
import sbtrelease.ReleasePlugin.autoImport._
import sbtrelease.ReleaseStateTransformations._
import sbtrelease._

object Release {

  val ReleaseSettings = Seq(
    releaseProcess := Seq[ReleaseStep](
        checkSnapshotDependencies,
        inquireVersions,
        setReleaseVersion,
        tagRelease,
        pushChanges,
        runClean, //do sbt clean to force rebuilding all existing artifacts to ensure reapplying the version build setting
        releaseStepCommandAndRemaining("+publish")
      )
  )
}
