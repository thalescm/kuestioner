package br.com.thalesmachado.kuestioner

class Parser {
    companion object {

        fun parse(clazz: Class<out Kuestionable>): String {

            return "$bracketsStart${getClassName(clazz)}${getClassFields(clazz)}$bracketsEnd"
        }
    }
}

val bracketsStart = "{"
val bracketsEnd = "}"

fun classesWithoutFields (vararg classes : Class<*>) : Boolean {
    return classes.all { it.declaredFields.size == 0 && it.fields.size == 0 }
}

fun getClassName(clazz: Class<*>): String = clazz.simpleName.toLowerCase()

fun  getClassFields(clazz: Class<out Kuestionable>) : String {
    if (classesWithoutFields(clazz)) return ""

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
