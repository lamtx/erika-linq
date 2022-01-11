package erika.core.linq.sqlite

import erika.core.linq.Expression

interface SetterScope {
    infix fun <T> Column<T>.set(value: T)
    infix fun <T> Column<T>.set(value: Expression<T>)
}
