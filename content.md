# Scala Native Workshop
-----
### Sample slides follow
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

---

### Potential slide for talking about working with pointers, dereferencing etc

Also possible to talk about GraalVM, JNI, unsafe

-----
# Goal for distribution

When users does `brew install tws`:

* Homebrew should use pre-linked binaries for the user's OS
* If no binaries are available, Homebrew should link the NIR code on the user's localhost to produce a binary 

---

# Distribution plan

* `sbt publishLocal` produces JAR files with NIR files
* Publish JARs to Nexus as usual
* Make CI produce linked binaries and update formula with necessary code

-----
# 7 - Coursier

> Pure artifact fetching
 
* Coursier takes maven coordinates as input
* Integrates with Scala Native
* Can produce natively linked artifact as output

![coursier](img/coursier-launch.gif)

--- 
# 7 - Coursier

Use [coursier](https://github.com/coursier/coursier) to link a macOS or Linux binary from the application JARs. Add your coursier command to the supplied `Makefile`.

## Goal

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

---

## Homebrew terminology

* `Bottle`: Pre-built binary for a given OS and CPU architecture
* `Tap`: Git repository containing `Formulas`
* `Formula`: Package definition

See the complete list in the [Formula cookbook](https://docs.brew.sh/Formula-Cookbook)

---

Create a homebrew formula for the application. Fill out the missing parts of the formula at `homebrew/src/main/ruby/tws.rb`

## Goal

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

Create scripts that are run by CI on pull requests and master branch

* `pr.sh`: Should generate binary for the operating system it's run on
* `master.sh`: Should push binaries to Bintray and update Formula on master

## Goal

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