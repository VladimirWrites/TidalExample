# 🎵 Tidal Example 🎶

Example project that shows how to create Tidal search functionality using the Deezer API

![Video Example](assets/recording.gif)

Project characteristics 🚀
-------
This project brings to the table set of best practices, tools, and solutions:

* 100% [Kotlin](https://kotlinlang.org/)
* Modern architecture (Clean Architecture, Multi-Module setup, Model-View-ViewModel)
* [Android Jetpack](https://developer.android.com/jetpack)
* Single-activity architecture, using the [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started)
* Dependency Injection

Built With 🛠
-------
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - Kotlin's way of way of writing asynchronous, non-blocking code
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying data changes
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes
  - [DataBinding](https://developer.android.com/topic/libraries/data-binding) - Allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically
  - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Dependency Injection library for Android that reduces the boilerplate of doing manual dependency injection in your project
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java.

Tested With 🔬
-------
- [JUnit 4](https://junit.org/junit4/) - Simple framework to write repeatable tests
- [mockito](https://github.com/mockito/mockito) - Most popular Mocking framework for unit tests
- [mockito-kotlin](https://github.com/nhaarman/mockito-kotlin) - Provides helper functions to work with Mockito in Kotlin
- [Truth](https://github.com/google/truth) - Makes your test assertions and failure messages more readable
- [Robolectric](https://github.com/robolectric/robolectric) - Runs tests in a simulated Android environment inside a JVM without the overhead of an emulator
- [Fragment Test](https://developer.android.com/guide/fragments/test) - Helps you run Fragment tests without dependencies on a specific parent activity or fragment
- [Espresso Core](https://developer.android.com/training/testing/espresso/) - Helps you write concise, beautiful, and reliable Android UI tests.

To run tests use: `./gradlew testDebugUnitTest`

## License

     Copyright 2021 Vladimir Jovanović

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.