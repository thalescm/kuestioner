package br.com.thalesmachado.kuestioner

data class APIModel(
        val query : String?,
        val operationName: String? = null,
        val variables: Map<String, Any>? = null
)