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


-----

# Minimal setup

The officially supported way:
```
sbt new scala-native/scala-native.g8
```
but we have got something special:
```
git clone git@github.com:Tapad/scala-native-workshop.git
```
+
```
brew install llvm # Needed to build
brew install bdw-gc re2 jansson # Optional, but will be required by this CLI. Please install these as well.
```

Note: bdw-gc is the garbage collector, re2 is regular expression support

-----

# Basic SBT commands
| | |
|-|-|
|compile	| Compile Scala code to NIR|
|run	    | Compile, link and run the generated binary|
|nativeLink	| Link NIR and generate native binary|

---

# Garbage Collection

## immix (default)

Immix is a mostly-precise mark-region tracing garbage collector.

## boehm

Conservative generational garbage collector.

## none (experimental)

Garbage collector that allocates things without ever freeing them.
Useful for short-running command-line applications or applications where garbage collections pauses are not acceptable.

---

## Is GraalVM a real threat to Scala Native?

Yes and no.

#### Scala Native key differentiators:

* Low-level memory semantics, with elegant support to arrays, structs, and pointer arithmetic

* One can switch from close-to-metal code to idiomatic Scala seamlessly

* More suitable for systems programming glue-up code

-----

### Tasks

* Browse the tasks at https://github.com/Tapad/scala-native-workshop
* Start on the first task

```sbtshell
# See the plan!
sbt> groll list

# Start on task one
sbt> groll initial

# See solution
sbt> groll next

# Run groll next again to proceed to next task (NB! This will overwrite your changes)
sbt> groll next
```

Note:

* Root project when groll next

-----

##### WITH *&#x2764;* FROM
# TAPAD Oslo!
