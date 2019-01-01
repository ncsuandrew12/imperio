# Introduction

The Imperio options library provides an easy way for Java applications to process command-line arguments without having to reproduce common logic. It allows applications to easily implement both single-char POSIX-style and long-form arguments.

# Instructions

To use Imperio, follow these instructions.

## 1 - Download Imperio

### 1-1 - Download source zip file

1. Download [https://github.com/ncsuandrew12/imperio/archive/master.zip](https://github.com/ncsuandrew12/imperio/archive/master.zip):  
 `wget https://github.com/ncsuandrew12/imperio/archive/master.zip`

1. Unzip the archive:  
 `unzip master.zip`

1. Move to Imperio directory:  
 `cd imperio-master/Imperio`

## 2 - Build Imperio

### 2-1 - Install Gradle

Install Gradle if you do not have it. Imperio is currently developed using Gradle 4.8, but other versions of Gradle will likely work.

### 2-2 - Build Imperio From Source

Compile Imperio into a jar:

`gradle release`

Example output:

```
[Imperio]$ gradle release

> Task :release
Jar available at: build/libs/Imperio-0.0.0.jar
Jar available at: build/libs/Imperio.jar
JavaDoc available at: build/libs/Imperio-javadoc.jar

BUILD SUCCESSFUL in 15s
3 actionable tasks: 3 executed
```

## 3 - Copy/Install Imperio

Copy `build/libs/Imperio.jar` (or the version-name copy) to wherever you need it.

Be sure to add this jar to your classpath and/or Ant/Gradle/Maven files.

# Documentation

Imperio's JavaDoc is included in the repo, and is also available online at:

[https://github.com/ncsuandrew12/imperio/blob/documentation/Imperio/DOCUMENTATION.md](https://github.com/ncsuandrew12/imperio/blob/documentation/Imperio/DOCUMENTATION.md)

# Examples

For reference, there are example applications and Eclipse projects located here:

[Imperio/projects/examples](Imperio/projects/examples)

These examples mostly revolve around implementing the "echo" Unix utility using the Imperio library.

For detailed explanations of the example applications, see the included [EXAMPLES.md](docs/EXAMPLES.md) file.

# License

Imperio is licensed under the MIT license. A copy of the license is available in the [LICENSE](LICENSE) file.

