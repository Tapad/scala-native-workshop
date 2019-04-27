package com.tapad.app

import com.softwaremill.sttp._
import com.tapad.common.Greeting
import json.JValue
import org.ekrich.config.{ConfigFactory, ConfigParseOptions, ConfigSyntax}
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

    implicit val backend: SttpBackend[Id, Nothing] = CurlBackend(verbose = conf.debug())

    ConfigFile.load() match {
      case Some(config) =>
        val repos: String = sttp.get(uri"https://api.github.com/user/repos")
          .header("User-Agent", "awesome")
          .header("Authorization", s"token ${config.authenticationToken}").send().unsafeBody
        val jvalue: List[GitHubRepo] = JValue.fromString(repos).toObject[List[GitHubRepo]]
        // https://github.com/MediaMath/scala-json/blob/master/docs/USAGE.md#case-class-usage
        println(jvalue.map(_.full_name))

      case None => println(s"Config not found ${ConfigFile.defaultConfigPath}")
    }
  }
}
