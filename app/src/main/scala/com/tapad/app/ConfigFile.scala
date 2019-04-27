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
      val bytes = Files.readAllBytes(configPath)
      val configString = new String(bytes)

      val config = ConfigFactory.parseString(configString)

      if (config.hasPath(AuthenticationToken))
        Some(ConfigFile(config.getString(AuthenticationToken)))
      else None
    } else None

  }
}
