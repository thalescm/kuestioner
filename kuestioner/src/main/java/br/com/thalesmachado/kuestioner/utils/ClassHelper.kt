package br.com.thalesmachado.kuestioner.utils

import br.com.thalesmachado.kuestioner.annotations.Queryable

fun getClassName(clazz: Class<*>): String = clazz.simpleName

fun isEmptyClass (vararg classes : Class<*>) : Boolean {
    return classes.all { it.declaredFields.size == 0 && it.fields.size == 0 }
}

fun classNotAnnotated(clazz: Class<*>): Boolean {
    return !clazz.isAnnotationPresent(Queryable::class.java)
}

fun getQueryParameterName(clazz: Class<*>) : String? {
    val query = clazz.getAnnotation(Queryable::class.java).query
    return if (query == "") null else query
}