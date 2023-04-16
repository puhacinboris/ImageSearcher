# Image Searcher - Native Android app
 
Image Searcher allows users to search and save images found on unsplash.com using unsplash API.<br><br>
Application follows Google’s practices for building android apps using fragments, navigation component, retorfit2, Kotlin Coroutines, dependency injection, MVVM architecture...<br><br>
While the app is free to use it requires an unsplash API key which can be obtained by registering on unsplash.com.<br><br>
Inside file gradle.properties on line 24 enter API key.<br><br>
By default, app starts with pictures of android, but this can be changed inside Constants file.<br><br>
```kotlin
// gradle.properties
unsplash_access_key = "ENTER-YOUR-API-KEY-HERE"

// Constants
const val DEFAULT_QUERY = "android"
```
<br>

![][gallery] &emsp;&emsp; ![][details] &emsp;&emsp; ![][details_night]

<br>

Many thanks to Florian Walther and his [channel](https://www.youtube.com/@codinginflow) for guidelines and passed knowledge.
#### Built with:

![Kotlin][kotlinRef] ![Android Studio][aStudioRef] ![XML][xmlRef]

[gallery]: https://github.com/puhacinboris/puhacinboris/blob/main/images/imageSearcher/image_searcher_app.png
[details]: https://github.com/puhacinboris/puhacinboris/blob/main/images/imageSearcher/image_searcher_details.png
[details_night]: https://github.com/puhacinboris/puhacinboris/blob/main/images/imageSearcher/image_searcher_details_night.png
[kotlinRef]: https://github.com/puhacinboris/puhacinboris/blob/main/images/kotlin.png "Kotlin"
[aStudioRef]: https://github.com/puhacinboris/puhacinboris/blob/main/images/android-studio.png "Android Studio"
[xmlRef]: https://github.com/puhacinboris/puhacinboris/blob/main/images/xml.png "XML"
