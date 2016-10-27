package br.com.thalesmachado.sample.models

import br.com.thalesmachado.kuestioner.annotations.Queryable

@Queryable
data class Viewer(
        val login: String
)

data class ViewerQuery (val query: String)
