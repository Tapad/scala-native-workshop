# Build tips

## Tasks for this step

* Create a Homebrew formula and expose the application through homebrew 
* Create an entrypoint that CI can invoke to link your application to macOS and Linux

## Hints

Start by creating a release process that works entirely on your localhost. You need to:

1. Publish JAR with NIRs (no changes needed for this, `sbt publishLocal` takes case of it)
2. Create a ZIP file containing the `Makefile` and publish this ZIP. This ZIP will be used as the artifact that homebrew downloads (the `url` setting) 
3. Replace `{{ version }}` and `{{ shasum }}` in the Homebrew formula with the newly released version and SHA-256 checksum of the zip file
4. Use `git` to push the updated formula to a Homebrew tap. See the [Taps] documentation, or see https://github.com/Tapad/homebrew-tap for an example (taps are just git repos)

Choose one of two approaches:

* Script over the tasks above (with bash for instance), CI would invoke this script in the release process
* Embed the entire process in `sbt` using for instance plugins like [sbt-native-packager] (to produce the ZIP) and [sbt-homebrew] (to interpolate the formula)  

An extended task is to set up a full release pipeline that would increment version numbers on release, produce the artifacts and push them to relevant stores. 

## Tips for developing

### Makefile

The [`Makefile`](makefile/src/main/resources/Makefile) provides a platform agnostic entrypoint for linking the application with [coursier]. Test it out:

```
$ sbt publishLocal
$ cd homebrew/src/main/resources
$ make VERSION=0.1.0-SNAPSHOT
$ ./app --version
```

### Homebrew

Link your local homebrew's ivy cache to your local user-wide ivy cache. You may then test snapshot builds without publishing them to remote stores

```
ln -s ~/.ivy2 ~/Library/Caches/Homebrew/java_cache/.ivy2
```

In order to build your local formula, use

```
brew install --build-from-source formula.rb
```

Homebrew requires that your host your artifact on a URL. For local testing, start a webserver in the directory where you publish the zip

```
cd ~/.ivy2/local/com.tapad.workshop/homebrew/
python -m SimpleHTTPServer 8080
```

If you don't want to use `localhost` in your formula, you can set up a host alias in `/etc/hosts`

[coursier]: https://get-coursier.io
[sbt-native-packager]: https://github.com/sbt/sbt-native-packager
[sbt-homebrew]: https://github.com/Tapad/sbt-homebrew
[Taps]: https://docs.brew.sh/Taps