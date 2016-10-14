package br.com.thalesmachado.kuestioner.examples

import br.com.thalesmachado.kuestioner.annotations.Queryable

@Queryable(query = "accountId")
class ModelWithSingleQuery(
        val name: String
)