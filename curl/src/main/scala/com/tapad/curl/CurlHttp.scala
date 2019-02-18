package com.tapad.curl

import scala.scalanative.native
import scala.scalanative.native.stdlib.{realloc, _}
import scala.scalanative.native.string.memcpy
import scala.scalanative.native.{CFunctionPtr, CSize, Ptr, _}

object CurlHttp {

  type CurlFetch = CStruct2[CString, CSize]

  // the process of obtaining the cUrl option codes is quite complicated
  // if you want to know more about it, see https://github.com/curl/curl/blob/master/include/curl/curl.h
  // here are all the necessary values:
  private val WriteData = 10001
  private val Url = 10002
  private val WriteFunction = 20011

  private val wdFunc = CFunctionPtr.fromFunction4(writeData)

  def get(url: String): String = native.Zone { implicit z =>
    val curl = CCurl.init
    CCurl.setopt(curl, Url, toCString(url))
    CCurl.setopt(curl, WriteFunction, wdFunc)
    val bodyResp = malloc(sizeof[CurlFetch]).cast[Ptr[CurlFetch]]
    !bodyResp._1 = calloc(4096, sizeof[CChar]).cast[CString]
    !bodyResp._2 = 0
    CCurl.setopt(curl, WriteData, bodyResp)

    CCurl.perform(curl)

    val responseBody = fromCString(!bodyResp._1)
    free(!bodyResp._1)
    free(bodyResp.cast[Ptr[CSignedChar]])
    responseBody
  }

  private def writeData(ptr: Ptr[Byte], size: CSize, nmemb: CSize, data: Ptr[CurlFetch]): CSize = {
    val index: CSize = !data._2
    val increment: CSize = size * nmemb
    !data._2 = !data._2 + increment
    !data._1 = realloc(!data._1, !data._2 + 1)
    memcpy(!data._1 + index, ptr, increment)
    !(!data._1).+(!data._2) = 0.toByte
    size * nmemb
  }
}
