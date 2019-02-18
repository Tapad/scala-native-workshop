# 5 - Curl based HTTP requests

### Goal
 
Send an HTTP request to the obtain your IP (e.g. `GET https://api.ipify.org`).
The application should print out the IP to standard output.

### Hints

The [com.tapad.curl.CCurl](curl/src/main/scala/com/tapad/curl/CCurl.scala) class
already contains a subset of functions exposed from [curl.h](https://github.com/curl/curl/blob/master/include/curl/curl.h).
Consider wrapping it up in a more convenient object.
C code examples for handling HTTP requests using curl can be found [here](https://curl.haxx.se/libcurl/c/example.html).
