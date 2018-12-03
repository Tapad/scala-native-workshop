package com.tapad.common

import slogging._

object Greeting extends LazyLogging {
  def greet(name: String, exclamation: Boolean = false): String = {
    logger.debug(s"Generating greeting for parameters name=$name, exclamation=$exclamation")
    s"Hello, $name${if (exclamation) "!" else ""}"
  }
}
