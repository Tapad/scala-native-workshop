package com.tapad.curl

import scala.scalanative.native._

private[curl] trait Curl {}

@link("curl")
@extern
private[curl] object CCurl {
  @name("curl_easy_init")
  def init: Ptr[Curl] = extern

  @name("curl_easy_cleanup")
  def cleanup(handle: Ptr[Curl]): Unit = extern

  @name("curl_easy_setopt")
  def setopt(handle: Ptr[Curl], option: CInt, parameter: Any): CInt = extern

  @name("curl_easy_perform")
  def perform(easy_handle: Ptr[Curl]): CInt = extern
}
