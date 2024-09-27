## bulbasaur

A DSL, which allows for SQL queries generation.

### Intent

*bulbasaur* was created to simplify process of writing SQL queries.

### Get started

##### Add repository

```kotlin
maven("https://repo.shiza.dev/releases")
```

##### Add dependency

```kotlin
implementation("dev.shiza:bulbasaur:1.4.0")
```

###### Annotation processor

Since version 1.4.0 of bulbasaur there is an implementation of automatically generated
queries in compilation time.

```kotlin
annotationProcessor("dev.shiza:bulbasaur-generator:1.4.0")
```
