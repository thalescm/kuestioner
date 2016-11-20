package br.com.thalesmachado.sample.models

import br.com.thalesmachado.kuestioner.annotations.Queryable

@Queryable(queries = "id")
data class Film(
        val title: String
)