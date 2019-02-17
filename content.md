# Scala Native Workshop
-----
# The LLVM Compiler Infrastructure

* Umbrella term, not an acronym. LLVM is the name of the project
* It's a closely coupled set of compilers, linkers, assemblers, debuggers, static analyzers etc. that works well together
* Can be used to compile various high level languages to various platforms, CPU architectures etc.  

---

# LLVM overview

Multiple "frontends" are compiled to an intermediary representation (IR) which is processed by various "backends"

![LLVM pipeline](img/LLVMCompiler1.png)


---
# LLVM and the JVM

> Despite its name, LLVM has little to do with traditional virtual machines.

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

* What is an application
* AOT and JIT compilers
* Intermediate representation (IR)
* Compiling vs linking applications
* Static and dynamic linking


-----
# What is Scala Native

* It's not a "native compiler"
* It's a LLVM frontend that generates IR (or NIR strictly speaking)

![LLVM pipeline](img/LLVMCompiler1.png) 

-----
# Build pipeline

![Build pipeline](img/nativeworkshop.png)

-----
# 7 - Coursier

> Pure artifact fetching

* Takes maven coordinates as input
* Integrates with Scala Native
* Can produce natively linked artifact as output

![coursier](img/coursier-launch.gif)

--- 
# 7 - Coursier

## Task

* Use [coursier](https://github.com/coursier/coursier) to link a macOS or Linux binary from the application JARs
* Add your coursier command to the supplied `Makefile`

## Goal

* Run `make` to generate binary (hard coded version number is fine for now)

## Verify

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

---

## Essential terminology

* `Bottle`: Pre-built binary for a given OS and CPU architecture
* `Tap`: Git repository containing `Formulas`
* `Formula`: Package definition

See the complete list in the [Formula cookbook](https://docs.brew.sh/Formula-Cookbook)

---

## Goal

* Create a homebrew formula that runs the Makefile you created in the previous step 

## Task

* Fill out the missing parts of the formula at `homebrew/src/main/ruby/tws.rb`

## Verify

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

## Task

* Create scripts that are run by CI on pull requests and master branch

## Goal

* Create a pre-linked binary for your operating system and surface it through homebrew

## Verify

* `brew install tws` should install `tws` from a pre-linked binary -- not build from source 

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