package com.tapad.app

import org.rogach.scallop._

class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {

  version("Scala Native Workshop (c) 2018 Tapad Inc.")

  banner("""Usage: ./scala-native-workshop-out [OPTION]... [name]
           |Options:
           |""".stripMargin)

  val exclamationMark =
    toggle(name = "exclamation", short = 'e', default = Some(false))

  val name = trailArg[String](name = "name")

  footer(
    "\nFor all other tricks, consult https://github.com/Tapad/scala-native-workshop.")
  verify()
}
