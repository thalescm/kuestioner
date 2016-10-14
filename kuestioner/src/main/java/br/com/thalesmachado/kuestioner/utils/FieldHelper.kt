package br.com.thalesmachado.kuestioner.utils

import java.lang.reflect.Field

internal val basicTypes = arrayOf(Double::class.java, Float::class.java, Long::class.java, Int::class.java, Short::class.java, Byte::class.java,
        Char::class.java, String::class.java, Boolean::class.java, Array<Any>::class.java)

internal fun isFieldPrimitive(field: Field): Boolean {
    return basicTypes.contains(field.type)
}