package br.com.thalesmachado.kuestioner.examples

import br.com.thalesmachado.kuestioner.annotations.Queryable

@Queryable
class SimpleNestedModel (
    val simpleModel : SimpleModel
)