package erika.core.linq

import erika.core.linq.sqlite.Column
import erika.core.linq.sqlite.InsertNullValueException

data class Setter<T>(val column: Column<T>, val value: Expression<T>)

infix fun <T> Column<T>.x(value: T): Setter<T> {
    if (isNonNull && value == null) {
        throw InsertNullValueException(this)
    }
    return Setter(this, LiteralExpression(value))
}

infix fun <T> Column<T>.x(expression: Expression<T>): Setter<T> {
    return Setter(this, expression)
}