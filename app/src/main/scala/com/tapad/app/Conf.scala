package com.tapad.app

import org.rogach.scallop._

class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {

  version("Scala Native Workshop (c) 2019 Tapad")

  banner("""Usage: ./app-out [OPTION]... [name]
           |Options:
           |""".stripMargin)

  val exclamationMark =
    toggle(name = "exclamation", short = 'e', default = Some(false))

  val name = trailArg[String](name = "name", default = Some("World"))

  footer(
    "\nAll the workshop material can be found on https://github.com/Tapad/scala-native-workshop.")
  verify()
}
