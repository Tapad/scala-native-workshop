# 1 - Hello, World!

Let's take a walk through a simple Scala Native application setup:
* [project/plugins.sbt](project/plugins.sbt)
* [build.sbt](build.sbt) 
 
The source code is split into library and application:
* library with a function that generates a greeting for a given name
* application that runs the function

### Clone and install prerequisites

```shell
git clone git@github.com:Tapad/scala-native-workshop.git
```

##### MacOS
```
# Needed to build
brew install llvm

# Optional, but will be required by this CLI. Please install these as well.
brew install bdw-gc re2 jansson
```

##### Ubuntu
```
sudo apt install clang libunwind-dev
sudo apt install libgc-dev libre2-dev # optional
```

### Run

To run the application from SBT one can just type:
```
> sbt 'app/run World!'
```

### Build

The application can be build using:
```
> sbt 'app/nativeLink'
```
and run via:
```
> cd ./app/target/scala-2.11
> ./app-out World
Hello, World!
```

### Worth looking

Check out intermediate representation in a `.nir` file.
NIR [docs](https://github.com/scala-native/scala-native/blob/master/docs/contrib/nir.rst). 

