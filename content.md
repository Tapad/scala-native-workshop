# Scala Native Workshop
-----
# Scala Native

> Scala Native is an optimizing ahead-of-time compiler and lightweight managed runtime designed specifically for Scala<br>
> _- scala-native.org_

Scala Native can take a Scala program with objects, traits, higher kinded types etc. and translate it down to the same the same kind of executable machine code that you would've got from a C compiler.

-----

# Scala Native

> Systems programming is the art of writing code while remaining aware of the properties and limitations of the machine that will run it.<br>
> _- Modern Systems Programming with Scala Native_

In addition, Scala Native adds powerful capabilities to work closer with the "bare metal"

* Elementary data types such as `struct`s, `pointer`s, low-level byte strings etc. are easy to work with. No need for `JNI` or `Unsafe`.
* Use system level shared libraries and C-style memory management
* Reuse existing tools and infrastructure in the Scala/Java ecosystem

Note: These techniques allows us to essentially replace C programs with Scala.

* Smaller memory footprint
* No JIT warmup phase at application startup
* Reuse the existing Scala and Java ecosystem
  * Build with SBT and publish to Maven Central as usual
  * Use ScalaTest and friends
    

-----
# In this workshop

* We're going to create a CLI that links with `libcurl` and invokes remote HTTP APIs
* Package and distribute the application source code as well as pre-linked binaries

Note: Don't worry about what "pre-linked binaries" mean

# Pawel: Getting started / minimal sbt project

Also possible to talk about GraalVM, JNI, unsafe

-----

### Task slides 4-7

-----

### Pointers, extern etc.

-----

### Task slides curl

-----
# Linking

The first version of our application is now finished. Now we only need to link it do the target operating systems we plan to support and distribute it.

### Plan

* Link the application up-front to a set of operating systems and CPU architectures
* Distribute source code along with linked binaries through Homebrew

Note: I promised to explain "pre-linked binaries". Let's see what's happening under the hood. 

-----
# What _is_ Scala Native

* Scala Native is not a "native compiler"
* It's a "LLVM frontend"

-----

# LLVM Compiler Infrastructure

> Despite its name, LLVM has little to do with traditional virtual machines.<br>
> _- llvm.org_

* Umbrella term, not an acronym. LLVM is the name of the project
* It's a "closely knit" set of compilers, linkers, assemblers, debuggers, static analyzers etc. that works well together
* Can be used to compile various high level languages to various platforms, CPU architectures etc.  

-----

# LLVM overview

Multiple "frontends" are compiled to an intermediary representation (IR) which is processed by various "backends"

![LLVM pipeline](img/LLVMCompiler1.png)

Note: This is not LLVM specific, all modern compilers looks like this

Compare with the JVM: Scala, Groovy, Jython etc.

Backends doesn't have to be AOT. Wide variety: There's also a JIT backend for IR, or you could compile C++ to Java Byte Code.

---
# LLVM and the JVM

* LLVM and the JVM are register and stack machines respectively
* LLVM IR is a much lower level representation than Java Byte Code
  * No garbage collection
  * No classes etc. in the intermediate representation\* 
* LLVM is more modular than the JVM, it support use cases such as
  * Compile Java code to native ARM or x86
  * Compile Haskell to PHP IR
* LLVM can perform AOT or compile to various intermediate formats
  * It also ships with its own JIT than runs on IR  
  
This is not good vs bad, it's just different approaches to producing assembly language

-----

# Terminology refresh

* What is an executable file, or "binary"?
* AOT and JIT compilers
* Intermediate representation (IR)
* Compiling vs linking applications
* Static and dynamic linking

Note:

Application: x86 + syscalls + executable file format (entry point, exit code, etc.)
AOT/JIT: Certain optimizations only possible in AOT and JIT respectively
Static & dynamic linking <=> Fat jars or not

-----
# Distribution plan

* `sbt package` produces JAR files with NIR files
* Publish JARs to Nexus as usual\*
* Make CI produce linked binaries and update formula with necessary code

\* In this workshop we're using localhost instead
-----
# 7 - Coursier

> Pure artifact fetching <br>+<br>JAR ⇒ Binary
 
* `Coursier` takes maven coordinates as input
* Integrates with Scala Native
* Can produce natively linked artifact as output

![coursier](img/coursier-launch.gif)

----- 
# 7 - Coursier

Use [coursier](https://github.com/coursier/coursier) to link a macOS or Linux binary from the application JARs. Add your coursier command to the supplied `Makefile`.

### Goal

* Run `make` to generate binary (hard coded version number is fine for now)

```bash
$ cd makefile/src/main/resources
$ make
$ ./tws wombat
Hello, wombat
Your IP is: 85.117.48.11
```
-----
# 8 - Homebrew 🍺

Homebrew is a package manager for Linux and macOS (and Windows through WSL)

We'll use homebrew to

* Ensure that dynamically linked libraries are available, install them if not
* Make pre-built binaries of our application available to our users

-----

## Homebrew terminology

* `Bottle`: Pre-built binary for a given OS and CPU architecture
* `Tap`: Git repository containing `Formulas`
* `Formula`: Package definition

See the complete list in the [Formula cookbook](https://docs.brew.sh/Formula-Cookbook)

---

Create a homebrew formula for the application. Fill out the missing parts of the formula at `homebrew/src/main/ruby/tws.rb`

### Goal

* Create a homebrew formula that runs the Makefile you created in the previous step 

```bash
$ sbt release
# Replace version number with what was released
$ brew install --build-from-source ~/.ivy2/local/com.tapad.workshop/homebrew/0.0.4/formulaes/tws.rb
```

-----
# 9 - Building bottles

So far we have what we need to assemble binaries for various platforms. Now we're going to to tie it all together.

![Build pipeline](img/nativeworkshop.png)

---

## 9 - Building bottles

Create scripts that are run by CI on pull requests and master branch

* `pr.sh`: Should generate binary for the operating system it's run on
* `master.sh`: Should push binaries to Bintray and update Formula on master

### Goal

* Create a pre-linked binary for your operating system and surface it through homebrew

Homebrew should use the bottle when clients install the application

```bash
$ brew install tws
==> Installing tws from jgogstad/testtap
==> Downloading https://dl.bintray.com/jgogstad/bottles-testtap/tws-0.5.0.high_sierra.bottle.tar.gz
######################################################################## 100.0%
==> Pouring tws-0.5.0.high_sierra.bottle.tar.gz
🍺  /usr/local/Cellar/tws/0.5.0: 3 files, 7.3MB
$
```

-----
# Power of Markdown in your presentation
Place you content in **content.md**.

Separate slides horizontally with **5 dashes** and vertically with **3 dashes**.
---
Welcome to the *basement*
-----
# headers
## headers
### headers
#### headers
##### headers
###### headers
-----
Nicely highlighted code...
```scala
/** Basic command line parsing. */
object Main {
  var verbose = false

  def main(args: Array[String]) {
    for (a <- args) a match {
      case "-h" | "-help"    =>
        println("Usage: scala Main [-help|-verbose]")
      case "-v" | "-verbose" =>
        verbose = true
      case x =>
        println("Unknown option: '" + x + "'")
    }
    if (verbose)
      println("How are you today?")
  }
}
```
...also the inlined one: `def foo(bar: String): Unit`.
-----
# Links
[http://tapad.com](http://tapad.com)
-----
# Images
![tapad-logo](img/TAPAD_eps_green.png)
-----
# Lists
* item 1
* item 2
* item 3
-----
# Notes
Speaker notes can be placed after `Note:`
Note: This will only appear in the speaker notes window.
-----   
<!-- .slide: data-background="#9B2743" -->
You can even change background...
-----
<!-- .slide: data-background="#B7BF10" style="color: #9B2743;" -->
...and font color.
-----
###### No more Power Point!

##### WITH *&#x2764;* FROM
# Data Platform Oslo!

`sbt new Tapad/reveal.js.g8`