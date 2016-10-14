package br.com.thalesmachado.kuestioner.examples

import br.com.thalesmachado.kuestioner.annotations.Queryable

@Queryable
class SimpleModelWithNoFieldClassAsField (
        val noFieldClass : QueryableWithNoFieldsModel
)