package br.com.thalesmachado.kuestioner.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.VALUE_PARAMETER)
annotation class Queryable (
    vararg val queries : String
)