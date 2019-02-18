# 8 - Homebrew

## Goal:

* Create a homebrew formula that runs the Makefile you created in step 7. 

## Before you start

We’ve provided a release setup that releases artifacts to your local ivy repository. Test out that it works, run `sbt release`. [sbt-dynver](https://github.com/dwijnand/sbt-dynver) is used to instruct sbt to read and write the version number from git tags. [sbt-release](https://github.com/dwijnand/sbt-dynver) is used for the release.

* Start a webserver at the parent directory of where the artifacts are released, this will serve as our “Nexus”

```
$ cd ~/.ivy2/local/com.tapad.workshop/
$ python -m SimpleHTTPServer 8080
``` 

## Task

* Fill out the missing parts of `tws.rb`, our formula

Hints

* See https://github.com/Homebrew/homebrew-core/blob/master/Formula/exa.rb for an example formula, ignore the bottle block
* Use `http://localhost:8080` for the URL, point it to the zip with the makefile
* Bind `homebrewFormulaChecksum` in sbt, see [sbt-homebrew](https://github.com/Tapad/sbt-homebrew)
  * Ignore anything that has to do with pushing to git

Official documentation

* https://docs.brew.sh/Formula-Cookbook

## Verify

```bash
sbt release
# Replace version number with what was released
brew install --build-from-source ~/.ivy2/local/com.tapad.workshop/homebrew/0.0.4/formulaes/tws.rb
```

