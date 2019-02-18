# 2 - Library and application

### Goal
 
Split the logic into library and application.
* Create a library with a function that generates a `greeting` for a given name.
* Build the binary, and run it outside of SBT.

### Worth looking

Check out intermediate representation in a `.nir` file.
NIR [docs](https://github.com/scala-native/scala-native/blob/master/docs/contrib/nir.rst). 

### Run

To run the application one can just type:
```
> sbt "app/run World!"
```

### Build

The application can be build using:
```
> sbt "app/nativeLink"
```
and run via:
```
> cd ./app/target/scala-2.11
> ./app-out World
Hello, World!
```
