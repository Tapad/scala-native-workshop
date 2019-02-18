# 2 - Parsing CLI arguments

### Task
 
Your application is supposed to parse CLI arguments.
* support toggle `--exclamation` which adds `!` after the greeting, and `name` the last parameter
* `--help` should print out a help message

### Hints

Please use Scallop for this task:
* add a library dependency for scallop, `"org.rogach" %%% "scallop" % "3.1.5"`
* follow the example at https://github.com/scallop/scallop#quick-example

NB! We can use regular Scala library (e.g. `scallop` in this case).
Normally in SBT, `%%` adds Scala version suffix (e.g. `_2.11`),
whereas `%%%` adds also Scala Native Version (e.g. `_0.3_2.11`).

### Run

To run the application one can just type:
```
> sbt 'app/run --exclamation World'
```
or just print help message using:
```
> sbt 'app/run --help'
```
