package br.com.thalesmachado.kuestioner

import br.com.thalesmachado.kuestioner.annotations.Queryable


val bracketsStart = "{"
val bracketsEnd = "}"


private fun getClassName(clazz: Class<*>): String = clazz.simpleName

private fun getQueryableClassName(clazz: Class<*>): String {
    val str = getClassName(clazz)
    return Character.toLowerCase(str[0]) + str.substring(1)
}

private fun classNotAnnotated(clazz: Class<*>): Boolean {
    return !clazz.isAnnotationPresent(Queryable::class.java)
}

private fun classesWithoutFields (vararg classes : Class<*>) : Boolean {
    return classes.all { it.declaredFields.size == 0 && it.fields.size == 0 }
}

private fun  getClassFields(clazz: Class<*>) : String {
    if (classesWithoutFields(clazz)) throw IllegalArgumentException("${getClassName(clazz)} must have at least one field")

    var returnable = bracketsStart
    clazz.fields.forEach {
        returnable += it.name
    }
    clazz.declaredFields.forEach {
        returnable += it.name
    }
    returnable.removeSuffix(" ")
    return returnable + bracketsEnd
}



class Parser {
    companion object {

        fun parse(clazz: Class<*>): String {
            if (classNotAnnotated(clazz)) throw IllegalArgumentException("${getClassName(clazz)} must be annotated with @Queryable")
            return "$bracketsStart${getQueryableClassName(clazz)}${getClassFields(clazz)}$bracketsEnd"
        }
    }
}