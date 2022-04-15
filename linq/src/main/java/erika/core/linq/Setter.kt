package erika.core.linq

import erika.core.linq.sqlite.Column
import erika.core.linq.sqlite.InsertNullValueException

data class Setter<T>(val column: Column<T>, val value: Expression<T>)

@Suppress("FUNCTIONNAME")
fun <T> Setter(column: Column<T>, value: T): Setter<T> {
    if (column.isNonNull && value == null) {
        throw InsertNullValueException(column)
    }
    return Setter(column, LiteralExpression(value))
}

infix fun <T> Column<T>.set(value: T): Setter<T> = Setter(this, value)

infix fun <T> Column<T>.set(expression: Expression<T>): Setter<T> = Setter(this, expression)