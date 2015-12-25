# Like Button

Like Button is a library that allows you to create a button with animation effects similar to [Twitter's heart](https://dribbble.com/shots/2416983-Twitter-Heart-Animation) when you like something.

![Icon animations](http://i.giphy.com/3o8dp4uq3K4vvR1MJO.gif "Icon animations")

---

# Table of Contents

1. [Gradle Dependency](https://github.com/jd-alexander/LikeButton#gradle-dependency)
   1. [Repository](https://github.com/jd-alexander/LikeButton#repository)
   2. [Dependency](https://github.com/jd-alexander/LikeButton#dependency)
2. [Basic Usage](https://github.com/jd-alexander/LikeButton#basic-usage)
   1. [Like Button XML](https://github.com/jd-alexander/LikeButton#like-button)
   2. [Attributes](https://github.com/jd-alexander/LikeButton#attributes)
3. [Like Event Listener](https://github.com/jd-alexander/LikeButton#like-listener)
4. [Icon Types](https://github.com/jd-alexander/LikeButton#icon-types)
   1. [XML](https://github.com/jd-alexander/LikeButton#icon-types-xml)
   2. [Java](https://github.com/jd-alexander/LikeButton#icon-types-java)
5. [Custom Icons](https://github.com/jd-alexander/LikeButton#custom-icons)
   1. [XML](https://github.com/jd-alexander/LikeButton#custom-icons-xml)
   2. [Java](https://github.com/jd-alexander/LikeButton#custom-icons-java)
6. [Circle Color Config](https://github.com/jd-alexander/LikeButton#circle-color-config)
   1. [XML](https://github.com/jd-alexander/LikeButton#circle-color-config-xml)
   2. [Java](https://github.com/jd-alexander/LikeButton#circle-color-config-java)
7. [Exploding Dots Color Config](https://github.com/jd-alexander/LikeButton#dots-color-config)
   1. [XML](https://github.com/jd-alexander/LikeButton#dots-color-config-xml)
   2. [Java](https://github.com/jd-alexander/LikeButton#dots-color-config-java)
8. [Inspiration](https://github.com/jd-alexander/LikeButton#inspiration)
9. [Contribution](https://github.com/jd-alexander/LikeButton#contribution)
10. [License](https://github.com/jd-alexander/LikeButton#license)

   
---

# Gradle Dependency

[ ![JitPack](https://img.shields.io/github/release/jd-alexander/likebutton.svg?label=jitpack) ](https://jitpack.io/#jd-alexander/likebutton)
[![Build Status](https://travis-ci.org/jd-alexander/LikeButton.svg)](https://travis-ci.org/jd-alexander/LikeButton)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)

#### Repository

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

#### Dependency

Add this to your module's `build.gradle` file:

```gradle
dependencies {
	...
	compile 'com.github.jd-alexander:LikeButton:0.1.1'
	}
}
```

---
