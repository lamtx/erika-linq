package erika.core.linq.sqlite

import android.database.Cursor
import erika.core.linq.NamedExpression
import erika.core.linq.ObjectFactory
import erika.core.linq.getObjectFactory

class Collector<out T>(
        private val source: T,
        private val cursor: Cursor
) {
    private val cache = mutableListOf<Column<*>>()
    private var index = 0
    private var prepared = false

    inline fun <reified R> get(noinline fieldName: T.() -> NamedExpression<R>): R {
        return get(fieldName, R::class.java)
    }

    @PublishedApi
    internal fun <R> get(fieldName: T.() -> NamedExpression<R>, clazz: Class<R>): R {
        val field = fieldName(source).name
        return if (prepared) {
            val column = try {
                cache[index]
            } catch (e: IndexOutOfBoundsException) {
                throw RuntimeException("Index out of bound. It usually happens when the number of field reading for each time are different", e)
            }
            index += 1
            if (column.name != field) {
                error("Column mismatch")
            }
            @Suppress("UNCHECKED_CAST")
            column.factory(cursor, column.index) as R
        } else {
            val columnIndex = cursor.getColumnIndex(field)
            val objectFactory = getObjectFactory(clazz)
            cache.add(Column(columnIndex, field, objectFactory))
            objectFactory(cursor, columnIndex)
        }
    }

    internal fun allSet() {
        prepared = true
        index = 0
    }

    private class Column<T>(val index: Int, val name: String, val factory: ObjectFactory<T>)
}
