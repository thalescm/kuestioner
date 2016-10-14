package br.com.thalesmachado.kuestioner

import br.com.thalesmachado.kuestioner.examples.NotAnnotatedModel
import br.com.thalesmachado.kuestioner.examples.QueryableWithNoFieldsModel
import br.com.thalesmachado.kuestioner.examples.SimpleModel
import org.junit.Test
import kotlin.test.assertEquals

class ParserTest {

    @Test
    fun emptyViewModel_shouldThrowError() {
        try {
            getQueryForClass(NotAnnotatedModel::class.java)
        } catch (e: IllegalArgumentException) {
            assertEquals("NotAnnotatedModel must be annotated with @Queryable", e.message)
        }
    }

    @Test
    fun testQueryableModelWithNoFields_showThrowError() {
        try {
            getQueryForClass(QueryableWithNoFieldsModel::class.java)
        } catch (e: IllegalArgumentException) {
            assertEquals("QueryableWithNoFieldsModel must have at least one field", e.message)
        }
    }

    @Test
    fun testSimpleModel() {
        assertEquals(
                "{simpleModel{name}}",
                getQueryForClass(SimpleModel::class.java)
        )
    }

    fun getQueryForClass(clazz: Class<*>): String {
        return Parser.parse(clazz)
    }
}