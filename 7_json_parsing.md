# 7 - JSON Parsing

[scala-json](https://github.com/MediaMath/scala-json) is a JSON parser compiled to Scala Native. We can use this for parsing JSON responses.

## Do this first 

* Install jansson (available in `brew`, `apt`, etc.)
* Issue a new github token from [github.com/settings/tokens](https://github.com/settings/tokens)
  * Give it access to read your repos
* Store the token in your configuration file

```
$ cat ~/.config/tws/tws.conf
authentication_token=my-token
```

## Task

* Parse the JSON response. Fill in the blanks in [Main.scala](app/src/main/scala/com/tapad/app/Main.scala)

