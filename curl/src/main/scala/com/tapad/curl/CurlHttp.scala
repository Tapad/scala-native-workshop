package com.tapad.curl

import scala.scalanative.native
import scala.scalanative.native.stdlib.{realloc, _}
import scala.scalanative.native.string.memcpy
import scala.scalanative.native.{CFunctionPtr, CSize, Ptr, _}

object CurlHttp {

  type MemoryStruct = CStruct2[CString, CSize]

  // the process of obtaining the cUrl option codes is quite complicated
  // if you want to know more about it, see https://github.com/curl/curl/blob/master/include/curl/curl.h
  // here are all the necessary values:
  private val WriteData = 10001
  private val Url = 10002
  private val WriteFunction = 20011

  private val writeMemoryCallbackFunction = CFunctionPtr.fromFunction4(writeMemoryCallback)

  def get(url: String): String = native.Zone { implicit z =>
    val curlHandle = CCurl.init
    CCurl.setopt(curlHandle, Url, toCString(url))
    CCurl.setopt(curlHandle, WriteFunction, writeMemoryCallbackFunction)
    val chunk = malloc(sizeof[MemoryStruct]).cast[Ptr[MemoryStruct]]
    !chunk._1 = calloc(128, sizeof[CChar]).cast[CString]
    !chunk._2 = 0
    CCurl.setopt(curlHandle, WriteData, chunk)

    CCurl.perform(curlHandle)

    val responseBody = fromCString(!chunk._1)
    free(!chunk._1)
    free(chunk.cast[Ptr[CSignedChar]])
    responseBody
  }

  private def writeMemoryCallback(contents: Ptr[Byte], size: CSize, nmemb: CSize, userp: Ptr[MemoryStruct]): CSize = ???
}