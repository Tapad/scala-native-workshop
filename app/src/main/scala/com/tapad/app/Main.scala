package com.tapad.app

import com.tapad.common.Greeting

object Main {
  def main(args: Array[String]): Unit = {
    val conf = new Conf(args)
    val greet = Greeting.greet(conf.name(), conf.exclamationMark())
    println(greet)
  }
}
