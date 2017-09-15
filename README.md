# Like Button 

[ ![JitPack](https://img.shields.io/github/release/jd-alexander/likebutton.svg?label=jitpack) ](https://jitpack.io/#jd-alexander/likebutton)
[![Build Status](https://travis-ci.org/jd-alexander/LikeButton.svg)](https://travis-ci.org/jd-alexander/LikeButton)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-LikeButton-green.svg?style=true)](https://android-arsenal.com/details/1/3038)


Like Button is a library that allows you to create a button with animation effects similar to [Twitter's heart](https://dribbble.com/shots/2416983-Twitter-Heart-Animation) when you like something.

![Icon animations](http://i.giphy.com/3o8dp4uq3K4vvR1MJO.gif "Icon animations")

---

# Table of Contents

1. [Gradle Dependency](https://github.com/jd-alexander/LikeButton#gradle-dependency)
   1. [Repository](https://github.com/jd-alexander/LikeButton#repository)
   2. [Dependency](https://github.com/jd-alexander/LikeButton#dependency)
2. [Basic Usage](https://github.com/jd-alexander/LikeButton#basic-usage)
   1. [Like Button XML](https://github.com/jd-alexander/LikeButton#like-button-xml)
   2. [Attributes](https://github.com/jd-alexander/LikeButton#attributes)
3. [Button State](https://github.com/jd-alexander/LikeButton#button-state)
4. [Like Event Listener](https://github.com/jd-alexander/LikeButton#like-event-listener)
5. [Icon Types](https://github.com/jd-alexander/LikeButton#icon-types)
6. [Icon Size](https://github.com/jd-alexander/LikeButton#icon-size)
7. [Custom Icons](https://github.com/jd-alexander/LikeButton#custom-icons)
8. [Circle Color Config](https://github.com/jd-alexander/LikeButton#circle-color-config)
9. [Dots Color Config](https://github.com/jd-alexander/LikeButton#dots-color-config)
10. [Animation Size](https://github.com/jd-alexander/LikeButton#animation-size)
11. [Inspiration](https://github.com/jd-alexander/LikeButton#inspiration)
12. [Contribution](https://github.com/jd-alexander/LikeButton#contribution)
13. [License](https://github.com/jd-alexander/LikeButton#license)

   
---

# Gradle Dependency


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
	compile 'com.github.jd-alexander:LikeButton:0.2.3'
	}
}
```

---

# Basic Usage

#### Like Button XML

To use this like button in your layout simply copy and paste the xml below. This provides the default heart button. 

```xml
<com.like.LikeButton
            app:icon_type="heart"
            app:icon_size="25dp"
            android:id="@+id/star_button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>
```

#### Attributes

There are several other attributes that can be used to configure the button's behaviour and appearance. They are shown below and will be explained in the sections that follow long with their java counterparts.

```xml
<com.like.LikeButton
app:icon_type="Star"
app:circle_start_color="@color/colorPrimary"
app:like_drawable="@drawable/thumb_on"
app:unlike_drawable="@drawable/thumb_off"
app:dots_primary_color="@color/colorAccent"
app:dots_secondary_color="@color/colorPrimary"
app:circle_end_color="@color/colorAccent"
app:icon_size="25dp"
app:liked="true"
app:anim_scale_factor="2"
app:is_enabled="false"
/>

```
---

# Button State

To set the inital liked state of the button you simply use the setLiked functionality via XML or Java. This will show the button in the liked state with the drawable that you selected.

#### XML

```
app:liked="true"
```

#### Java


```java

likeButton.setLiked(true);
```

You can also set if the button is to be enabled or disabled. Once disabled, the animation, listener or any other functionality of the button won't be triggered.

#### XML

```
app:is_enabled="false"
```

#### Java


```java

likeButton.setEnabled(false);
```



---

# Like Event Listener

To listen to events from your like button, simply implement the listener that's triggered once the button is tapped.

```java

likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });

```

---

# Icon Types

The libary is bundled with three icons that you can use. A heart,thumb and a star. 

#### XML

To set the respective icon via xml simply use the word in the icon type attribute.

```
app:icon_type="heart"
```

#### Java

If you would like to set the icon via Java then simply call the set icon method on the button. 

```java

likeButton.setIcon(IconType.Star);
```
---

# Icon Size

#### XML

To set the icon size via xml simply use this attribute

```xml
app:icon_size="20dp"
```

#### Java

If you are doing it programmatically you can set it with either the method for pixels or dp. 

```java
likeButton.setIconSizePx(40);
likeButton.setIconSizeDp(20);
```
Note, it's very important that the size of the button is set even if you are planning to use custom drawables as described below as the library uses this value to determine the width and height of the effects that occur when the button is tapped.

---

# Custom Icons

#### XML

In order to use custom icons instead of the ones bundled with the library you simply set the drawables that represent the liked and unliked state of the button.

```
app:like_drawable="@drawable/thumb_on"
app:unlike_drawable="@drawable/thumb_off"
```

#### Java

```java
likeButton.setLikeDrawable(heart_on);
likeButton.setUnlikeDrawable(heart_off);

likeButton.setUnlikeDrawableRes(R.drawable.heart_off);
likeButton.setLikeDrawableRes(R.drawable.heart_on);
```
---

# Circle Color Config

If you watch the animation closely you will notice that there's a circle that begins from the center of the icon and and then it animates away from the center before the exploding dots animate. These colours can be changed to suit the theme of your icon.

#### XML

```
app:circle_start_color="@color/colorPrimary"
app:circle_end_color="@color/colorAccent"
```

#### Java

```java
likeButton.setCircleEndColorRes(R.color.colorAccent);
likeButton.setCircleEndColorRes(R.color.colorPrimary);
```
---

# Dots Color Config

The dots that represent the outer animation can be coloured also.

#### XML

```
app:dots_primary_color="@color/colorAccent"
app:dots_secondary_color="@color/colorPrimary"
```

#### Java

```
likeButton.setExplodingDotColorsRes(R.color.colorPrimary,R.color.colorAccent);
```
---


# Animation Size

To change the size of the dots that also contributes to the size of the overall like button view you can use the animation scale factor attribute via XML or it's Java equivalent

#### XML

```
app:anim_scale_factor="2.5"

```

#### Java

```
likeButton.setAnimationScaleFactor(2);
```
---


# Inspiration

This library was made by possible based on code and design inspiration from these sources:

https://github.com/frogermcs/LikeAnimation

https://github.com/lightsmeki/android_twitter_heart_animation_button

https://dribbble.com/shots/2416983-Twitter-Heart-Animation



# Contribution


Please fork repository and contribute using pull requests.

Any contributions, large or small, major features, bug fixes, additional language translations, unit/integration tests are welcomed and appreciated but will be thoroughly reviewed and discussed.


# License

    Copyright 2016 Joel Dean

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
