# **Kuestioner**
A lightweight kotlin query builder and client for [GraphQL](http://graphql.org/)

-----------------

[![Build Status](https://travis-ci.org/thalescm/kuestioner.svg?branch=master)](https://travis-ci.org/thalescm/kuestioner) [ ![Download](https://api.bintray.com/packages/thalescmachado/maven/kuestioner/images/download.svg) ](https://bintray.com/thalescmachado/maven/kuestioner/_latestVersion)

-----------------

# Warning
THIS LIBRARY IS STILL IN EXPERIMENTAL PHASE. But I'm open to contributions and ideas!

-----------------

### Basic usage

```kotlin
// Define the models you want to query for (they can be nested) with the `@Queryable` annotation
@Queryable
class Person (
    val name : String,
    val age : Int
)
```

Then use Kuestioner to build the query for this class

```kotlin
val query = Kuestioner.queryOn(Person::class.java)
```

 The result will be a kotlin object corresponding to:
 ```JSON
 {
   "query" : "{ person {name age} }"
 }
```

### Query with parameters

```kotlin
@Queryable(query = "id")
class Person (
    val name : String,
    val age : Int
)
```

Then use Kuestioner to build the query for this class

```kotlin
val query = Kuestioner.queryOn(
  Person::class.java,
  mapOf("id" to "12")
)
```

The result will be

```JSON
{
  "query" : "{ person(id: 12) {name age}}"
}
```

### Installation

Add the dependency:
```gradle
dependencies {
    compile 'br.com.thalesmachado:kuestioner:0.1.1'
}
```

### RoadMap

- **Make a complete API following graphQL language**:
- **Integrate with Retrofit to enable this as a parameter to requests, and parse the response accordingly**
