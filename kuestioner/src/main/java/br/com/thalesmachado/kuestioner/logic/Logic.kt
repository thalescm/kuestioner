package br.com.thalesmachado.kuestioner.logic

import br.com.thalesmachado.kuestioner.constants.*
import br.com.thalesmachado.kuestioner.utils.getClassName
import br.com.thalesmachado.kuestioner.utils.getQueryParameterName
import br.com.thalesmachado.kuestioner.utils.isEmptyClass
import br.com.thalesmachado.kuestioner.utils.isFieldPrimitive
import java.lang.reflect.Field

internal fun getQueryForFieldAsClass (field : Field) : String {
    return field.name + getQueryForFields(field.type) + space
}

internal fun getQueryForPrimitiveField (field: Field) : String {
    return field.name + space
}

internal fun getQueryForFields(clazz: Class<*>): String {
    if (isEmptyClass(clazz)) throw IllegalArgumentException("${getClassName(clazz)} must have at least one field")

    var returnable = bracketsStart
    clazz.declaredFields.forEach {
        if (isFieldPrimitive(it))
            returnable += getQueryForPrimitiveField(it)
        else
            returnable += getQueryForFieldAsClass(it)
    }
    returnable = returnable.removeSuffix(space)
    returnable += bracketsEnd
    if (returnable.length == 2) returnable = "" // case where class has no fields
    return returnable
}

internal fun getQueryForClass(clazz: Class<*>, queries: Map<String, Any>): String {
    val str = getClassName(clazz)
    var returnable = Character.toLowerCase(str[0]) + str.substring(1)

    getQueryParameterName(clazz)?.let {
        if (queries[it] != null) {
            returnable += parenthesisStart + it + colon + queries[it] + parenthesisEnd
        } else {
            throw IllegalArgumentException("${getClassName(clazz)} must have a value to query for $it")
        }
    }

    return returnable
}

internal fun getQueryForClassAndFields(clazz: Class<*>, queries: Map<String, Any>): String {
    return "${getQueryForClass(clazz, queries)}${getQueryForFields(clazz)}"
}