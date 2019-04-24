package com.tapad.curl

import scala.scalanative.native
import scala.scalanative.native.stdlib.{realloc, _}
import scala.scalanative.native.string.memcpy
import scala.scalanative.native.{CFunctionPtr, CSize, Ptr, _}
import PThread._

object CurlHttp {

  type CurlFetch = CStruct2[CString, CSize]

  private val WriteData = 10001
  private val Url = 10002
  private val WriteFunction = 20011

  private val CURL_GLOBAL_SSL       = 1<<0
  private val CURL_GLOBAL_WIN32     = 1<<1
  private val CURL_GLOBAL_ALL       = CURL_GLOBAL_SSL|CURL_GLOBAL_WIN32
  private val CURL_GLOBAL_NOTHING   = 0
  private val CURL_GLOBAL_DEFAULT   = CURL_GLOBAL_ALL
  private val CURL_GLOBAL_ACK_EINTR = 1<<2

  private val wdFunc = CFunctionPtr.fromFunction4(writeData)

  type RequestResponse = CStruct2[CString, CString]

  def getSerial(urls: List[String]): List[String] =
    urls.map(get)

  def get(urls: List[String]): List[String] = native.Zone { implicit z =>
    CCurl.globalInit(CURL_GLOBAL_DEFAULT)

    val requestResponse = malloc(sizeof[RequestResponse] * urls.length).cast[Ptr[RequestResponse]]
    val threads = malloc(sizeof[pthread_t] * urls.length).cast[Ptr[pthread_t]]

    urls.zipWithIndex.map {
      case (el, i) =>
        val r = requestResponse + i
        !r._1 = toCString(el)
        val l = threads + i
        createThread(l, null, getAndStore, r.cast[Ptr[Byte]])
    }

    urls.indices.toList.map { i =>
      val thread = !(threads + i)
      joinThread(thread, null)
      val response = requestResponse + i
      fromCString(!response._2)
    }
  }

  def get(url: String): String = native.Zone { implicit z =>
    fromCString(cget(toCString(url)))
  }

  def getAndStore: CFunctionPtr1[Ptr[Byte], Ptr[Byte]] = (in: Ptr[Byte]) => {
    val req = in.cast[Ptr[RequestResponse]]
    val result = cget(!req._1)
    !req._2 = result
    null
  }

  def cget(url: CString): CString = {
    val curl = CCurl.init
    CCurl.setopt(curl, Url, url)
    CCurl.setopt(curl, WriteFunction, wdFunc)
    val bodyResp = malloc(sizeof[CurlFetch]).cast[Ptr[CurlFetch]]
    !bodyResp._1 = calloc(4096, sizeof[CChar]).cast[CString]
    !bodyResp._2 = 0
    CCurl.setopt(curl, WriteData, bodyResp)

    CCurl.perform(curl)

    val responseBody = !bodyResp._1
    free(bodyResp.cast[Ptr[CSignedChar]])
    responseBody
  }

  private def writeData(ptr: Ptr[Byte],
                        size: CSize,
                        nmemb: CSize,
                        data: Ptr[CurlFetch]): CSize = {
    val index: CSize = !data._2
    val increment: CSize = size * nmemb
    !data._2 = !data._2 + increment
    !data._1 = realloc(!data._1, !data._2 + 1)
    memcpy(!data._1 + index, ptr, increment)
    !(!data._1).+(!data._2) = 0.toByte
    size * nmemb
  }
}
