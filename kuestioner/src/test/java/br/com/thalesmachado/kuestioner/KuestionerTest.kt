package br.com.thalesmachado.kuestioner

import br.com.thalesmachado.kuestioner.examples.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class KuestionerTest {

    @Test
    fun emptyViewModel_shouldThrowError() {
        try {
            getQueryForClass(NotAnnotatedModel::class.java)
            failOnExceptionNotThrown()
        } catch (e: IllegalArgumentException) {
            assertEquals("NotAnnotatedModel must be annotated with @Queryable", e.message)
        }
    }

    @Test
    fun testQueryableModelWithNoFields_showThrowError() {
        try {
            getQueryForClass(QueryableWithNoFieldsModel::class.java)
            failOnExceptionNotThrown()
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

    @Test
    fun testSimpleModelManyFields() {
        assertEquals(
                "{simpleModelMultiplePrimitiveFields{name age}}",
                getQueryForClass(SimpleModelMultiplePrimitiveFields::class.java)
        )
    }

    @Test
    fun testQueryShouldThrowIfNoParameters() {
        try {
            getQueryForClass(ModelWithSingleQuery::class.java)
            failOnExceptionNotThrown()
        } catch (e: IllegalArgumentException) {
            assertEquals("ModelWithSingleQuery must have a value to query for accountId", e.message)
        }
    }

    @Test
    fun testQueryWithRightParameter() {
        assertEquals("{modelWithSingleQuery(accountId:12){name}}",
                getQueryForClass(ModelWithSingleQuery::class.java,
                        mapOf("accountId" to "12")
                ))
    }

    fun getQueryForClass(clazz: Class<*>, queries: Map<String, Any> = mapOf()): String {
        return Kuestioner.queryOn(clazz, queries)
    }

    fun failOnExceptionNotThrown() {
        fail("should have thrown the exception")
    }
}