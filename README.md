<h1 align="center">try parse JSON in android</h1>
<p align="center">android app that already setup to play with elementary parsing JSON without any additional libraries</p>

### getting started

```
git clone https://github.com/locovna/i-just-wanna-try-to-parse-f-JSON-in-android.git
```
1. import project (`abitofjson` folder) via Android Studio
2. change `REQUEST_URL`
3. implement parsing in `extractSomethingFromJson`



### where is it
```java
//in MainActivity change request url
  private static final String REQUEST_URL = "";
```



```java
//at the bottom of MainActivity you can find method wich you're interested in
private Something extractSomethingFromJson(String somethingJSON) {
  ...
  try {

    //replace code here

  } catch{...}
}
```

### additional info

[here you can find full guide](http://goldenpears.github.io/i-just-wanna-try-to-parse-f-JSON-in-android/)

highly inspired by [udacity course](https://www.udacity.com/course/android-basics-networking--ud843)
