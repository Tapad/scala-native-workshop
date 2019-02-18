addSbtPlugin("de.heikoseeberger" % "sbt-groll" % "6.1.0")

addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.3.8")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.15")

addSbtPlugin("com.tapad.sbt" %% "sbt-homebrew" % "0.6")

addSbtPlugin("com.dwijnand" % "sbt-dynver" % "3.1.0")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.11")

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
resolvers += "Tapad Aggregate" at "https://nexus.tapad.com/repository/aggregate"