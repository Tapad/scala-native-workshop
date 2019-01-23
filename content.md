# Scala Native Workshop
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