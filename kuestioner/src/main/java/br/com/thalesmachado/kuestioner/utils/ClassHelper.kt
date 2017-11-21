package br.com.thalesmachado.kuestioner.utils

import br.com.thalesmachado.kuestioner.annotations.Named
import br.com.thalesmachado.kuestioner.annotations.Queryable

internal fun getClassName(clazz: Class<*>): String = clazz.simpleName

internal fun lowerCamelCase(clazz: Class<*>): String {
    val str = getClassName(clazz)
    return Character.toLowerCase(str[0]) + str.substring(1)
}

internal fun isEmptyClass(vararg classes : Class<*>) : Boolean {
    return classes.all { it.declaredFields.isEmpty() && it.fields.isEmpty() }
}

internal fun classNotAnnotated(clazz: Class<*>): Boolean {
    return !clazz.isAnnotationPresent(Queryable::class.java)
}

internal fun getQueriesParameterName(clazz: Class<*>) : Array<out String> {
    return clazz.getAnnotation(Queryable::class.java).queries
}

internal fun getNamedParameter(clazz: Class<*>) : String? {
    val named = clazz.getAnnotation(Named::class.java)
    return named?.name
}
