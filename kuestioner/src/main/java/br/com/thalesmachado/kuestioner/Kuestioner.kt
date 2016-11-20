package br.com.thalesmachado.kuestioner

import br.com.thalesmachado.kuestioner.annotations.Queryable
import br.com.thalesmachado.kuestioner.constants.bracketsEnd
import br.com.thalesmachado.kuestioner.constants.bracketsStart
import br.com.thalesmachado.kuestioner.logic.getQueryForClassAndFields
import br.com.thalesmachado.kuestioner.utils.classNotAnnotated
import br.com.thalesmachado.kuestioner.utils.getClassName

private fun throwIfNotAnnotated(clazz: Class<*>) {
    if (classNotAnnotated(clazz)) throw IllegalArgumentException("${getClassName(clazz)} must be annotated with @Queryable")
}

class Kuestioner {
    companion object {

        fun queryOn(@Queryable clazz: Class<*>, withParameters: Map<String, Any> = mapOf()): APIModel {
            throwIfNotAnnotated(clazz)

            return APIModel(query = "$bracketsStart${getQueryForClassAndFields(clazz, withParameters)}$bracketsEnd")
        }
    }
}