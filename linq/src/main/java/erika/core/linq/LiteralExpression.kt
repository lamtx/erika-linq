package erika.core.linq

class LiteralExpression<T>(value: T) : Expression<T> {
    val value: Any? = toSQLiteLiteral(value)

    override fun clause(context: Context): String {
        return if (value == null) "NULL" else "?"
    }

    override fun args(): Array<Any?> {
        return if (value == null) arrayOf() else arrayOf(value)
    }
}

val LiteralExpression<Boolean>.boolValue: Boolean
    get() = value == 1

