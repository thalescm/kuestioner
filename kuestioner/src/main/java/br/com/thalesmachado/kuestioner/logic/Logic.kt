package br.com.thalesmachado.kuestioner.logic

import br.com.thalesmachado.kuestioner.constants.*
import br.com.thalesmachado.kuestioner.utils.getClassName
import br.com.thalesmachado.kuestioner.utils.getQueryParameterName
import br.com.thalesmachado.kuestioner.utils.isEmptyClass

fun getQueryForFields(clazz: Class<*>): String {
    if (isEmptyClass(clazz)) throw IllegalArgumentException("${getClassName(clazz)} must have at least one field")

    var returnable = bracketsStart
    clazz.fields.forEach {
        returnable += it.name + space
    }
    clazz.declaredFields.forEach {
        returnable += it.name + space
    }
    return returnable.removeSuffix(space) + bracketsEnd
}

fun getQueryForClass(clazz: Class<*>, queries: Map<String, Any>): String {
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

fun getQueryForClassAndFields(clazz: Class<*>, queries: Map<String, Any>): String {
    return "${getQueryForClass(clazz, queries)}${getQueryForFields(clazz)}"
}