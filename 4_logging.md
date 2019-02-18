# 4 - Logging

### Motivation

Majority of Scala applications depend on logging framework inherited from JVM ecosystem.
We cannot use them in Scala Native, as such have to look for another solution.

### Goal
 
Add logging capabilities to your application.
The application should support running in a debug mode if `--debug` arguments is passed in.

### Hints

Consider [Slogging](https://github.com/jokade/slogging#scala-native) or your own solution.