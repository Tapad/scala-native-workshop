# 4 - Curl based HTTP requests

### Task
 
Send an HTTP request to the obtain your IP (e.g. `GET https://api.ipify.org`).
The application should print out the IP to standard output.

The solution can be based on C code example for handling HTTP requests using curl from [here](https://curl.haxx.se/libcurl/c/getinmemory.html).

Majority of the code is already provided. Let's take a look how we have linked Curl in [com.tapad.curl.CCurl](curl/src/main/scala/com/tapad/curl/CurlHttp.scala).

To accomplish the task you must implement function writing the body of response into memory buffer in class [com.tapad.curl.CurlHttp](curl/src/main/scala/com/tapad/curl/CurlHttp.scala).

---
## Note on interoperability with C

#### Basic memory management

```scala
Zone { implicit z =>
  val buffer = alloc[Byte](n)
}
```
or well-known `stdlib` and `libc` functions:
```scala
def malloc(size: CSize): Ptr[Byte]
```
```scala
def calloc(num: CSize, size: CSize): Ptr[Byte]
```
```scala
def realloc(ptr: Ptr[Byte], newSize: CSize): Ptr[Byte]
```
```scala
def free(ptr: Ptr[Byte]): Unit
```
```scala
def memcpy(dst: RawPtr, src: RawPtr, count: CSize): RawPtr
```

---

#### Handling `String`s

```scala
def toCString(str: String)(implicit z: Zone): CString
```

```scala
def fromCString(cstr: CString,
                charset: Charset = Charset.defaultCharset()): String
```

```scala
val msg: CString = c"Hello, world!"
```

---

#### Extern objects and linking libraries

```scala
@native.link("mylib")
@native.extern
object mylib {
  def f(): Unit = native.extern
}
```

---

#### Structs

```scala
type MyStructWith2Fields = CStruct2[CString, CString]
```

---

### Pointers:

| Operation	        | C syntax  |   Scala Syntax |
|-|-|-|
| As function argument | char* | Ptr[CChar] |
| Load value        | *ptr |	!ptr |
| Store value       | *ptr = value |	!ptr = value |
| Load at index     | ptr[i]	| ptr(i) |
| Store at index	| ptr[i] = value	| ptr(i) = value |
| Load a field      | ptr->name	| !ptr._N |
| Store a field     | ptr->name = value	| !ptr._N = value |

---

#### Function pointers

the following signature in C:
```scala
void foo(void (* f)(char *));
```
can be declared as following:

```scala
def foo(f: CFunctionPtr1[CString, Unit]): Unit = native.extern
```

To pass a Scala function to CFunctionPtr1, you need to use the conversion function CFunctionPtr.fromFunction1():

```scala
def f(s: CString): Unit = ???
foo(CFunctionPtr.fromFunction1(f))
```

---

##### For more details, go to the [documentation](http://www.scala-native.org/en/v0.3.8/user/interop.html#interop).
