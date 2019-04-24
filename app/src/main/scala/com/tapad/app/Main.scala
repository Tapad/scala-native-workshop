package com.tapad.app

import com.softwaremill.sttp._
import com.tapad.common.Greeting
import com.tapad.curl.CurlHttp
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

    val result = CurlHttp.get(List(
      "https://jsonplaceholder.typicode.com/todos/1",
      "https://jsonplaceholder.typicode.com/todos/2",
      "https://jsonplaceholder.typicode.com/todos/3",
      "https://jsonplaceholder.typicode.com/todos/77"
    ))
    println(result.mkString("\n"))

//
//    implicit val backend: SttpBackend[Id, Nothing] = CurlBackend(verbose = conf.debug())
//    val ip = sttp.get(uri"https://api.ipify.org").send().unsafeBody
//
//    println(s"Your IP is: $ip")
  }
}
