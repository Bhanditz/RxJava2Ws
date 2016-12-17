# RxJavaWs 

[RxJava2](http://github.com/ReactiveX/RxJava) wrapper for [OkHttp](https://github.com/square/okhttp) websocket.

Download
--------
Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Add the dependency:
```groovy
dependencies {
    compile 'com.github.tagantroy:RxJava2Ws:0.1'
}
```

TODO:
-------
1. Filters(StringMessageFilter pass only String Messages)
2. Serializer/Deserializer (GSON)


Site
-------
https://tagantroy.github.io/RxJava2Ws/

