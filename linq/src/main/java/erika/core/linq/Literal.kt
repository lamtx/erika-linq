package erika.core.linq

import android.content.ContentValues
import android.database.Cursor
import erika.core.linq.sqlite.Column
import java.util.*

internal typealias ObjectFactory<T> = (cursor: Cursor, columnIndex: Int) -> T

private fun <T> enumFactory(clazz: Class<T>): ObjectFactory<T?> {
    return { cursor, columnIndex ->
        val enumConstants = clazz.enumConstants!!

        if (cursor.isNull(columnIndex)) {
            null
        } else {
            val ordinal = cursor.getInt(columnIndex)
            if (ordinal < 0 || ordinal >= enumConstants.size) {
                null
            } else {
                enumConstants[ordinal]
            }
        }
    }
}

private val stringFactory: ObjectFactory<String?> = { cursor, columnIndex ->
    cursor.getString(columnIndex)
}

private val doubleFactory: ObjectFactory<Double?> = { cursor, columnIndex ->
    val value = cursor.getDouble(columnIndex)
    if (value == 0.0 && cursor.isNull(columnIndex)) {
        null
    } else {
        value
    }
}

private val intFactory: ObjectFactory<Int?> = { cursor, columnIndex ->
    val value = cursor.getInt(columnIndex)
    if (value == 0 && cursor.isNull(columnIndex)) {
        null
    } else {
        value
    }
}

private val longFactory: ObjectFactory<Long?> = { cursor, columnIndex ->
    val value = cursor.getLong(columnIndex)
    if (value == 0L && cursor.isNull(columnIndex)) {
        null
    } else {
        value
    }
}

private val floatFactory: ObjectFactory<Float?> = { cursor, columnIndex ->
    val value = cursor.getFloat(columnIndex)
    if (value == 0f && cursor.isNull(columnIndex)) {
        null
    } else {
        value
    }
}

private val boolFactory: ObjectFactory<Boolean?> = { cursor, columnIndex ->
    if (cursor.isNull(columnIndex)) {
        null
    } else {
        cursor.getInt(columnIndex) != 0
    }
}

private val dateFactory: ObjectFactory<Date?> = { cursor, columnIndex ->
    if (cursor.isNull(columnIndex)) {
        null
    } else {
        Date(cursor.getLong(columnIndex))
    }
}

private val blobFactory: ObjectFactory<ByteArray?> = { cursor, columnIndex ->
    cursor.getBlob(columnIndex)
}

@Suppress("UNCHECKED_CAST")
internal fun <T> getObjectFactory(type: Class<T>): ObjectFactory<T> {
    return when (type) {
        String::class.java -> stringFactory as ObjectFactory<T>
        Int::class.java, java.lang.Integer::class.java -> intFactory as ObjectFactory<T>
        Date::class.java -> dateFactory as ObjectFactory<T>
        Boolean::class.java, java.lang.Boolean::class.java -> boolFactory as ObjectFactory<T>
        Double::class.java, java.lang.Double::class.java -> doubleFactory as ObjectFactory<T>
        Float::class.java, java.lang.Float::class.java -> floatFactory as ObjectFactory<T>
        Long::class.java, java.lang.Long::class.java -> longFactory as ObjectFactory<T>
        ByteArray::class.java -> blobFactory as ObjectFactory<T>
        else -> if (Enum::class.java.isAssignableFrom(type)) {
            enumFactory(type) as ObjectFactory<T>
        } else {
            throw UnsupportedOperationException("Unsupported type ${type.name} in cursor")
        }
    }
}

internal fun toSQLiteLiteral(value: Any?): Any? {
    return when (value) {
        is Boolean -> if (value) 1 else 0
        is Enum<*> -> value.ordinal
        is Date -> value.time
        null, is Number, is String, is ByteArray -> value
        else -> throw UnsupportedOperationException("Unsupported type ${value.javaClass} for SQL")
    }
}

internal fun putToContentValues(values: ContentValues, column: Column<*>, literal: LiteralExpression<*>) {
    val name = column.name
    when (val value = literal.value) {
        null -> values.put(name, null as String?)
        is String -> values.put(name, value)
        is Int -> values.put(name, value)
        is Double -> values.put(name, value)
        is Long -> values.put(name, value)
        is Float -> values.put(name, value)
        is Byte -> values.put(name, value)
        is Short -> values.put(name, value)
        is Boolean -> values.put(name, value)
        is ByteArray -> values.put(name, value)
        else -> throw error("Unknown type ${value.javaClass} for SQL")
    }
}