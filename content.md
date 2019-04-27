# Scala Native Workshop
-----
# Scala Native

> Scala Native is an optimizing ahead-of-time compiler and lightweight managed runtime designed specifically for Scala<br>
> _- scala-native.org_

Scala Native can take a Scala program with objects, traits, higher kinded types etc. and translate it down to the same the same kind of executable machine code that you would've got from a C compiler.

---

# LLVM

Multiple "frontends" are compiled to an intermediary representation (IR) which is processed by various "backends"

![LLVM pipeline](img/LLVMCompiler1.png)

You can write C-style programs in Scala Native, with all the performance and all the drawbacks 

Note: This is not LLVM specific, all modern compilers looks like this

Backends doesn't have to be AOT. Wide variety: There's also a JIT backend for IR, or you could compile C++ to Java Byte Code.

If 

---

# Scala Native

Scala Native adds powerful capabilities to work closer with the "bare metal"

* Elementary data types such as `struct`s, `pointer`s, low-level byte strings etc. are easy to work with. No need for `JNI` or `Unsafe`.
* Use system level shared libraries and C-style memory management
* Reuse existing tools and infrastructure in the Scala/Java ecosystem

Note: These techniques allows us to essentially replace C programs with Scala.

* Smaller memory footprint
* No JIT warmup phase at application startup
* Reuse the existing Scala and Java ecosystem
  * Build with SBT and publish to Maven Central as usual
  * Use ScalaTest and friends
    
Write critical path with manual memory management. Example folding vs reusing memory. 

-----

# In this workshop

* We're going to create a CLI that invokes remote HTTP APIs
* Get hands on with manual memory management 


```sbtshell
# See the plan!
sbt> groll list
```

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
sbt> groll initial
```

To see the solution

```sbtshell
# This will overwrite your changes!
sbt> groll next
```

-----

# 4 - Interoperability with C

---

### Basic memory management

```scala
Zone { implicit z =>
  val buffer = alloc[Byte](n)
}
```
or well-known `stdlib` and `libc` functions:
```scala
def malloc(size: CSize): Ptr[Byte]
```
```scala
def calloc(num: CSize, size: CSize): Ptr[Byte]
```
```scala
def realloc(ptr: Ptr[Byte], newSize: CSize): Ptr[Byte]
```
```scala
def free(ptr: Ptr[Byte]): Unit
```
```scala
def memcpy(dst: RawPtr, src: RawPtr, count: CSize): RawPtr
```

---

### Handling `String`s

```scala
def toCString(str: String)(implicit z: Zone): CString
```

```scala
def fromCString(cstr: CString,
                charset: Charset = Charset.defaultCharset()): String
```

```scala
val msg: CString = c"Hello, world!"
```

---

### Extern objects and linking libraries

```scala
@native.link("mylib")
@native.extern
object mylib {
  def f(): Unit = native.extern
}
```

---

### Structs

```scala
type MyStructWith2Fields = CStruct2[CString, CString]
```

---

### Pointers:

| Operation	        | C syntax  |   Scala Syntax |
|-|-|-|
| As function argument | char* | Ptr[CChar] |
| Load value        | *ptr |	!ptr |
| Store value       | *ptr = value |	!ptr = value |
| Load at index     | ptr[i]	| ptr(i) |
| Store at index	| ptr[i] = value	| ptr(i) = value |
| Load a field      | ptr->name	| !ptr._N |
| Store a field     | ptr->name = value	| !ptr._N = value |

---

### Function pointers

the following signature in C:
```scala
void foo(void (* f)(char *));
```
can be declared as following:

```scala
def foo(f: CFunctionPtr1[CString, Unit]): Unit = native.extern
```

To pass a Scala function to CFunctionPtr1, you need to use the conversion function CFunctionPtr.fromFunction1():

```scala
def f(s: CString): Unit = ???
foo(CFunctionPtr.fromFunction1(f))
```

---

# For more details, go to the [documentation](http://www.scala-native.org/en/v0.3.8/user/interop.html#interop).


-----
# Linking

The first version of our application is now finished. Now we only need to link it do the target operating systems we plan to support and distribute it.

### Plan

* Link the application up-front to a set of operating systems and CPU architectures
* Distribute source code along with linked binaries through Homebrew

-----
# Distribution plan

* `sbt package` produces JAR files with NIR files
* Publish JARs to Nexus as usual\*
* Make CI produce linked binaries and update formula with necessary code

\* In this workshop we're using localhost instead
-----
# 8 - Coursier

> Pure artifact fetching <br>+<br>JAR ⇒ Binary
 
* `Coursier` takes maven coordinates as input
* Integrates with Scala Native
* Can produce natively linked artifact as output

![coursier](img/coursier-launch.gif)

----- 
# 8 - Coursier

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
# 9 - Homebrew 🍺

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
# 10 - Building bottles

So far we have what we need to assemble binaries for various platforms. Now we're going to to tie it all together.

![Build pipeline](img/nativeworkshop.png)

---

## 10 - Building bottles

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

##### WITH *&#x2764;* FROM
# TAPAD Oslo!
