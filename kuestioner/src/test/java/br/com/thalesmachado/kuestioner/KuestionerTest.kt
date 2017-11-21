package br.com.thalesmachado.kuestioner

import br.com.thalesmachado.kuestioner.annotations.Named
import br.com.thalesmachado.kuestioner.annotations.Queryable
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

@Suppress("unused")
class KuestionerTest {

    private class NotAnnotatedModel

    @Test
    fun emptyViewModel_shouldThrowError() {
        try {
            getQueryForClass(NotAnnotatedModel::class.java)
            failOnExceptionNotThrown()
        } catch (e: IllegalArgumentException) {
            assertEquals("NotAnnotatedModel must be annotated with @Queryable", e.message)
        }
    }


    @Queryable
    private class AnnotatedWithNoParameters

    @Test
    fun testQueryableModelWithNoFields_showThrowError() {
        try {
            getQueryForClass(AnnotatedWithNoParameters::class.java)
            failOnExceptionNotThrown()
        } catch (e: IllegalArgumentException) {
            assertEquals("AnnotatedWithNoParameters must have at least one field", e.message)
        }
    }


    @Queryable
    private class OneField(val name: String)

    @Test
    fun testSimpleModel_shouldWork() {
        assertEquals(
                "{oneField{name}}",
                getQueryForClass(OneField::class.java).query
        )
    }


    @Queryable
    private class TwoFields(
            val name: String,
            val age: Int
    )

    @Test
    fun testSimpleModel_WithManyFields_ManyFields() {
        assertEquals(
                "{twoFields{name age}}",
                getQueryForClass(TwoFields::class.java).query
        )
    }

    @Queryable("accountId")
    private class OneParameter(
            val name: String
    )

    @Test
    fun testQueryShouldThrowIfNoParameters() {
        try {
            getQueryForClass(OneParameter::class.java)
            failOnExceptionNotThrown()
        } catch (e: IllegalArgumentException) {
            assertEquals("OneParameter must have a value to query for accountId", e.message)
        }
    }

    @Test
    fun testQueryWithOneParameter() {
        assertEquals("{oneParameter(accountId:12){name}}",
                getQueryForClass(OneParameter::class.java,
                        mapOf("accountId" to 12)
                ).query)
    }

    @Queryable("query1", "query2")
    private class TwoParameters(
            val name: String
    )

    @Test
    fun testQueryWithTwoParameters_WithOneNotProvided_ThrowError() {
        try {
            getQueryForClass(TwoParameters::class.java,
                    mapOf("query1" to 12)
            )
            failOnExceptionNotThrown()
        } catch (e: IllegalArgumentException) {
            assertEquals("TwoParameters must have a value to query for query2", e.message)
        }
    }

    @Test
    fun testQueryWithTwoParameters() {
        assertEquals("{twoParameters(query1:12,query2:\"string\"){name}}",
                getQueryForClass(TwoParameters::class.java,
                        mapOf("query1" to 12,
                                "query2" to "string")
                ).query)
    }

    @Queryable
    private class NonPrimitiveFieldWithoutFields(
            val noFieldClass: AnnotatedWithNoParameters
    )

    @Test
    fun testQueryableModelWithNoFieldClassAsField_showThrowError() {
        try {
            getQueryForClass(NonPrimitiveFieldWithoutFields::class.java)
            failOnExceptionNotThrown()
        } catch (e: IllegalArgumentException) {
            assertEquals("AnnotatedWithNoParameters must have at least one field", e.message)
        }
    }


    @Queryable
    private class NestedQueryables(
            val simpleModel: OneField
    )

    @Test
    fun testNestedSimpleModel() {
        assertEquals(
                "{nestedQueryables{simpleModel{name}}}",
                getQueryForClass(NestedQueryables::class.java).query
        )
    }

    @Queryable
    @Named("customName")
    private class NamedQuery(
            val name: String
    )

    @Test
    fun testQueryableWithCustomName() {
        assertEquals("{customName{name}}", getQueryForClass(NamedQuery::class.java).query)
    }

    fun getQueryForClass(clazz: Class<*>, queries: Map<String, Any> = mapOf()): APIModel {
        return Kuestioner.queryOn(clazz, queries)
    }

    fun failOnExceptionNotThrown() {
        fail("should have thrown the exception")
    }
}