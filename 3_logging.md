# 3 - Logging

Majority of Scala applications depend on logging framework inherited from JVM ecosystem.
We can not use them in Scala Native, so we have to look for another solution.

### Task
 
Add logging capabilities to your application.
The application should support running in a verbose mode if `--verbose` argument is passed in.
Please log greetings generation at `INFO` level. 

### Hints

Consider [Slogging](https://github.com/jokade/slogging#scala-native).
