package br.com.thalesmachado.kuestioner.logic

import br.com.thalesmachado.kuestioner.constants.*
import br.com.thalesmachado.kuestioner.utils.*
import java.lang.reflect.Field

internal fun getQueryForFieldAsClass (field : Field) : String {
    return "${field.name}${getQueryForFields(field.type)} "
}

internal fun getQueryForPrimitiveField (field: Field) : String {
    return "${field.name} "
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
    val returnable = getNamedParameter(clazz) ?: lowerCamelCase(clazz)

    var parameters = parenthesisStart
    for (query: String in getQueriesParameterName(clazz)) {
        if (queries[query] != null) {
            val wrapInQuotes = if (queries[query] is String) "\"${queries[query]}\"" else queries[query]
            parameters += "$query:$wrapInQuotes,"
        } else {
            throw IllegalArgumentException("${getClassName(clazz)} must have a value to query for $query")
        }
    }
    parameters = parameters.removeSuffix(comma)
    parameters += parenthesisEnd
    if (parameters.length == 2) parameters = "" // case where class has no fields

    return returnable + parameters
}

internal fun getQueryForClassAndFields(clazz: Class<*>, queries: Map<String, Any>): String {
    return "${getQueryForClass(clazz, queries)}${getQueryForFields(clazz)}"
}