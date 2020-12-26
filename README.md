LiveTask
======================

LiveTask is an open source Android library that helps you handle asynchronous requests in a simples way.

# Features

Using LiveTask library you can
* combine several request and have access to the total result
* have the cause of every failed request
* sync your User Interface with the state of any requests you have sent via out *reactToTask* BindingAdapter

# Download & Setup
**Minimum SDK Version** - Glide requires a minimum SDK version of 16 (Jelly Bean) or higher.

**Compile SDK Version** - Glide must be compiled against SDK version 30.

You can download a jar from GitHub's releases page.

Or use Gradle:

```gradle
repositories {
  google()
  jcenter()
}

dependencies {
  implementation 'com.github.HosseinMirzaei47:livetask:0.0.2'
}
```

Or Maven:

```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
    <groupId>com.github.HosseinMirzaei47</groupId>
    <artifactId>livetask</artifactId>
    <version>Tag</version>
</dependency>
```

The app is written entirely in Kotlin and uses the Gradle build system.

# How do I use LiveTask
