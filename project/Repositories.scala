import sbt._

object Repositories {

  val TapadSnapshots = "Tapad Nexus Snapshots" at "https://nexus.tapad.com/repository/snapshots"

  val TapadReleases = "Tapad Nexus Releases" at "https://nexus.tapad.com/repository/releases"

  val TapadAggregate = "Tapad Aggregate" at "https://nexus.tapad.com/repository/aggregate"

  val LocalMaven = "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository"

}
