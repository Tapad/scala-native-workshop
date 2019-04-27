package com.tapad.app

import com.softwaremill.sttp._
import com.tapad.common.Greeting
import slogging._

object Main {

  LoggerConfig.factory = TerminalLoggerFactory()

  def main(args: Array[String]): Unit = {
    val conf = new Conf(args)

    if (conf.verbose()) {
      LoggerConfig.level = LogLevel.DEBUG
    }

    val greet = Greeting.greet(conf.name(), conf.exclamationMark())
    println(greet)

    ConfigFile.load() match {
      case Some(config) => println(s"Read config $config")
      case None => println(s"Config not found ${ConfigFile.defaultConfigPath}")
    }

    implicit val backend: SttpBackend[Id, Nothing] = CurlBackend(verbose = conf.verbose())
    val ip = sttp.get(uri"https://api.ipify.org").send().unsafeBody

    println(s"Your IP is: $ip")
  }
}
