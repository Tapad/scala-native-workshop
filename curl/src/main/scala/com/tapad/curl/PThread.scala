package com.tapad.curl

import scala.scalanative.native._

@link("pthread")
@extern
object PThread {

  type pthread_t = Ptr[CStruct0]
//  type pthread_t = CStruct3[Long, Ptr[Byte], CChar]
  type pthread_attr_t = CStruct0

  @name("pthread_create")
  def createThread(thread: Ptr[pthread_t],
             attr: Ptr[pthread_attr_t],
             start_routine: CFunctionPtr1[Ptr[Byte], Ptr[Byte]],
             arg: Ptr[Byte]): CInt = extern

  @name("pthread_join")
  def joinThread(thread: pthread_t, value_ptr: Ptr[Ptr[Byte]]): CInt = extern
}
