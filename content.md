# Scala Native Workshop

Note:

* In this workshop, very hands on
* Create a CLI with common components
* Get hands on with programming with pointers and memory management 

-----
# Scala Native

> Scala Native is an optimizing ahead-of-time compiler and lightweight managed runtime designed specifically for Scala<br>
> _- scala-native.org_

Scala Native can take a Scala program with objects, traits, higher kinded types etc. and translate it down to the same the same kind of executable machine code that you would've got from a C compiler.

Note:

* Multi-platform languages are all the rage these days
* Scala native is an AOT compiler for Scala
* It means …

---

# LLVM

Multiple "frontends" are compiled to an intermediary representation (IR) which is processed by various "backends"

![LLVM pipeline](img/LLVMCompiler1.png)

You can write C-style programs in Scala Native, with all the performance and all the drawbacks 

Note: 

* It happens through a compiler infrastructure called LLVM 
* This is the same compiler infrastructure that is used for …
* Meaning you can write C programs in Scala and get …
* You get all the performance, but have to pay 

---

# Scala Native

Scala Native adds powerful capabilities to work closer with the "bare metal"

* Elementary data types such as `struct`s, `pointer`s, low-level byte strings etc. are easy to work with. No need for `JNI` or `Unsafe`.
* Example: `Ptr[Int]`
* Use system level shared libraries and C-style memory management
* Reuse existing tools and infrastructure in the Scala/Java ecosystem

Note:

* SN gives useful primitives for low-level programming, for example …, not need JNI
* Since we're running as assembly level, we have no runtime, so no type information and no reflection
* Typically when using SN, … C-libraries, high-level shell, low-level performance

Why?

* Smaller memory footprint
* No JIT warmup phase at application startup
* Reuse the existing Scala and Java ecosystem
  * Build with SBT and publish to Maven Central as usual
  * Use ScalaTest and friends
     

-----

# In this workshop

* We're going to create a CLI that invokes remote HTTP APIs
* Get hands on with manual memory management 


### [Workshop repository](https://github.com/tapad/scala-native-workshop)