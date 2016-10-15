# **Kuestioner**
A lightweight kotlin client for GraphQL

-----------------

# Warning
THIS LIBRARY IS STILL IN EXPERIMENTAL PHASE. But I'm open to contrubutions and ideas!

-----------------

### Basic usage

```kotlin
// Define the models you want to query for (they can be nested) with the `@Queryable` annotation
@Queryable
class Person (
    val name : String,
    val age : Int
)

// Then use Kuestioner to build the query for this class
val query = Kuestioner.queryOn(Person::class.java)

/*
 The result will be
 "
 {
   person {
     name
     age
   }
 }
 "
/*
```

### Query with parameters

In order to add more extras to the intent, one can do the following:
```kotlin
@Queryable(query = "id")
class Person (
    val name : String,
    val age : Int
)

val query = Kuestioner.queryOn(
  Person::class.java,
  mapOf("id" to "12")
  )

/*
 The result will be
 "
 {
   person(id: 12) {
     name
     age
   }
 }
 "
/*
```

### Installation

Add the dependency:
```gradle
dependencies {
    compile 'br.com.thalesmachado:kuestioner:0.0.1'
}
```

### RoadMap

- **Make a complete API following graphQL language**:
- **Integrate with Retrofit to enable this as a parameter to requests, and parse the response accordinly**
