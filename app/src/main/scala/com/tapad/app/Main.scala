package com.tapad.app

import com.tapad.common.Greeting
import slogging._

object Main {

  LoggerConfig.factory = TerminalLoggerFactory()

  def main(args: Array[String]): Unit = {
    val conf = new Conf(args)

    if (conf.debug()) {
      LoggerConfig.level = LogLevel.DEBUG
    }

    val greet = Greeting.greet(conf.name(), conf.exclamationMark())
    println(greet)
  }
}
