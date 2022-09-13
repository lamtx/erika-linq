package erika.core.linq

interface Filterable<T : Expressible> : Queryable<T> {
    fun where(expression: WhereScope.(T) -> Expression<Boolean>): Filterable<T>
}

interface WhereScope {

    val True: Expression<Boolean>

    val False: Expression<Boolean>

    companion object : WhereScope {
        override val True: Expression<Boolean>
            get() = LiteralExpression(true)

        override val False: Expression<Boolean>
            get() = LiteralExpression(false)
    }
}

@Suppress("FUNCTIONNAME")
internal fun <T : Expressible> Filterable(
    source: T,
    whereClauses: List<Expression<Boolean>> = emptyList(),
): Filterable<T> = object : Filterable<T> {
    override val source: T = source

    override fun where(expression: WhereScope.(T) -> Expression<Boolean>): Filterable<T> {
        var predicate = WhereScope.expression(source)
        if (predicate is NamedExpression<Boolean>) {
            predicate = predicate eq true
        } else if (predicate is LiteralExpression<Boolean> && predicate.boolValue) {
            return this
        }
        return Filterable(source, whereClauses + predicate)
    }

    override fun clause(context: Context): String {
        if (whereClauses.isEmpty()) {
            return ""
        }
        var appendAnd = false
        return buildString {
            append("WHERE ")
            for (clause in whereClauses) {
                if (appendAnd) {
                    append(" AND ")
                }
                appendAnd = true
                append("(").append(clause.clause(context)).append(")")
            }
        }
    }

    override fun args(): Array<Any?> {
        val args = mutableListOf<Any?>()

        for (exp in whereClauses) {
            args.addAll(exp.args())
        }

        return args.toTypedArray()
    }
}