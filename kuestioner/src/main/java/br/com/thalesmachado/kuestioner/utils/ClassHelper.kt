package br.com.thalesmachado.kuestioner.utils

import br.com.thalesmachado.kuestioner.annotations.Queryable

internal fun getClassName(clazz: Class<*>): String = clazz.simpleName

internal fun isEmptyClass (vararg classes : Class<*>) : Boolean {
    return classes.all { it.declaredFields.size == 0 && it.fields.size == 0 }
}

internal fun classNotAnnotated(clazz: Class<*>): Boolean {
    return !clazz.isAnnotationPresent(Queryable::class.java)
}

internal fun getQueryParameterName(clazz: Class<*>) : String? {
    val query = clazz.getAnnotation(Queryable::class.java).query
    return if (query == "") null else query
}