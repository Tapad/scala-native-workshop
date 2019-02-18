# 5 - Using STTP for HTTP requests

Instead of using libcurl directly, one can use STTP, which provides nicer Api, while invoking libcurl under-the-hood.

### Task
 
Send the request from ex. 5 using [STTP](https://github.com/softwaremill/sttp/blob/master/docs/backends/native/curl.rst).
Mind that you must install `libidn` and `curl` in version 7.56.0 or newer.
Take a look at [this](https://github.com/softwaremill/sttp#building--testing-the-scala-native-backend
) note.

##### MacOS

```shell
brew install libidn curl
```
