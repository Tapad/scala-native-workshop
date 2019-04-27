package com.tapad.app

import java.nio.file.{Files, Paths}

import org.ekrich.config.ConfigFactory

case class ConfigFile(authenticationToken: String)

object ConfigFile {

  lazy val userHome = System.getProperty("user.home")
  lazy val defaultConfigPath = s"$userHome/.config/tws/tws.conf"

  val AuthenticationToken = "authentication_token"

  def load(): Option[ConfigFile] = {
    val configPath = Paths.get(defaultConfigPath)

    if (Files.exists(configPath)) {
      // Follow the example in https://github.com/ekrich/sconfig/blob/master/docs/SCALA-NATIVE.md

      // Read file into a string and then ConfigFactory.parseString
      ???
    } else None

  }
}
