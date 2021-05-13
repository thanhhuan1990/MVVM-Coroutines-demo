# MVVM + Coroutines + Retrofit + Room Demo

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development

### Prerequisites
0. Get latest stable Android Studio

### Running the Project
1. Clone the project: master - production build
2. Open the project in Android Studio
3. add file to root project: 
    
    apikey.properties ```AppId = "${app ID}"```

4. Select the build variant (e.g. devDebug)
5. Run and enjoy! 🎉

## Architecture
This section describes the choices that I made and the future direction that we would like to take for the App
1. Kotlin
I decided to use Kotlin sparingly at first (think POJOs, Helpers), then moved on to designing new features.
2. Kotlin MVVM + Data Binding
    * Logic inside XMLs are kept to a minimum, common logic is then applied using @BindingAdapter
3. Hilt
    * Hilt provides a standard way to use DI in your application by providing containers for every Android class in your project and managing their lifecycles automatically. Hilt is built on top of the popular DI library Dagger to benefit from the compile-time correctness, runtime performance, scalability, and Android Studio support that Dagger provides
4. Coroutines
    * A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously.
5. Room
    * The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
6. Navigation
    * The navigation component provides a new type of navigation in android development, where we have a navigation graph to see all the screens and the navigation between them.
7. Lifecycle
    * Lifecycle-aware components perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.

### Components Diagram
![Architecture](https://user-images.githubusercontent.com/11939679/111912417-0185a880-8a9c-11eb-80a4-1b29fb78a4b9.png)

### ER Diagram
![Database ER diagram (crow's foot)](https://user-images.githubusercontent.com/11939679/111912432-19f5c300-8a9c-11eb-8c7b-0d2b1764ba42.png)

### Folder Structure
![Screen Shot 2021-03-21 at 23 28 20](https://user-images.githubusercontent.com/11939679/111912639-23335f80-8a9d-11eb-9338-1e634cc939da.png)

## Requirement Checklist
1. Programming language: Kotlin
2. Design app's architecture: MVVM powered by Coroutines, Retrofit and Room
3. Apply LiveData mechanism
4. UI same as in attachment.
5. UnitTests 96%, coverage by jacoco
6. Acceptance Tests: main feature of application can be automation via androidTest test case
7. API exception is handled
8. Caching mechanism by Room
9. Secure Android app:
    * Proguard: Proguard is free Java class file shrinker, optimizer, obfuscator, and preverifier. It detects and removes unused classes, fields, methods, and attributes.
    * Private api keys: App ID is private and do not public in source code. Developer must import file ```apikey.properties``` with correct key to make api call success.
10. Accessibility for Disability Supports:
    * TalkBack: 
        - All of UI elements have been added for support screen reader.
        - User can click on forecast item to hear the content.
    * Scaling text: all of Ui elements are supported `autoTextSize` to make it auto scale follow setting's font size
11. ER diagram and Solution diagrams for the components are attached above.
12. You are reading it.
