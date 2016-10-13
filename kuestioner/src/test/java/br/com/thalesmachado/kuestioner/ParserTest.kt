package br.com.thalesmachado.kuestioner

import org.junit.Test

class ParserTest {


    @Test
    fun testBasicModelParsing () {
        val kuery = Parser.parse(Model::class.java)

        assertEquals(
                "{model {name}}",
                kuery
        )
    }
}