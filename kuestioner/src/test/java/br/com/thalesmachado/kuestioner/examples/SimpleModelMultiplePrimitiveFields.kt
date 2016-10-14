package br.com.thalesmachado.kuestioner.examples

import br.com.thalesmachado.kuestioner.annotations.Queryable

@Queryable
class SimpleModelMultiplePrimitiveFields (
        val name : String,
        val age : Int
)