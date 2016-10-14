package br.com.thalesmachado.kuestioner

import br.com.thalesmachado.kuestioner.annotations.Queryable

val bracketsStart = "{"
val bracketsEnd = "}"
val parenthesisStart = "("
val parenthesisEnd = ")"
val colon = ":"
val space = " "

private fun getClassName(clazz: Class<*>): String = clazz.simpleName

private fun getQueryParamFromClass (clazz: Class<*>) : String? {
    val query = clazz.getAnnotation(Queryable::class.java).query
    return if (query.equals("")) null else query
}

private fun getQueryableClassName(clazz: Class<*>, queries :Map<String, Any> = mapOf()): String {
    val str = getClassName(clazz)
    var returnable = Character.toLowerCase(str[0]) + str.substring(1)

    getQueryParamFromClass(clazz)?.let {
        if (queries[it] != null) {
            returnable += parenthesisStart + it + colon + queries[it] + parenthesisEnd
        } else {
            throw IllegalArgumentException("${getClassName(clazz)} must have a value to query for $it")
        }
    }

    return returnable
}

private fun classNotAnnotated(clazz: Class<*>): Boolean {
    return !clazz.isAnnotationPresent(Queryable::class.java)
}

private fun throwIfNotAnnotated (clazz: Class<*>) {
    if (classNotAnnotated(clazz)) throw IllegalArgumentException("${getClassName(clazz)} must be annotated with @Queryable")
}

private fun classesWithoutFields (vararg classes : Class<*>) : Boolean {
    return classes.all { it.declaredFields.size == 0 && it.fields.size == 0 }
}

private fun  getClassFields(clazz: Class<*>) : String {
    if (classesWithoutFields(clazz)) throw IllegalArgumentException("${getClassName(clazz)} must have at least one field")

    var returnable = bracketsStart
    clazz.fields.forEach {
        returnable += it.name + space
    }
    clazz.declaredFields.forEach {
        returnable += it.name + space
    }
    return returnable.removeSuffix(space) + bracketsEnd
}



class Parser {
    companion object {

        fun parse(clazz: Class<*>, queries :Map<String, Any> = mapOf()): String {
            throwIfNotAnnotated(clazz)

            return "$bracketsStart${getQueryableClassName(clazz, queries)}${getClassFields(clazz)}$bracketsEnd"
        }
    }
}