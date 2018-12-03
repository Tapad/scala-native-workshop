package com.tapad.app

import com.tapad.common.Greeting

object Main {
  def main(args: Array[String]): Unit =
    println(Greeting.greet(args.head))
}
