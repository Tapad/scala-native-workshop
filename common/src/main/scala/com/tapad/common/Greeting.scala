package com.tapad.common

object Greeting {
  def greet(name: String, exclamation: Boolean = false): String =
    s"Hello, $name${if (exclamation) "!" else ""}"
}
