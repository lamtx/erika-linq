package erika.core.linq

interface Context {
    fun <T : Selectable> from(source: T): Filterable<T>

    fun <T : Selectable> T.query(): Filterable<T>

    infix fun <Left : Selectable, Right : Selectable> Left.join(other: Right): JoinStep<Left, Right>

    fun alias(source: Selectable): String?

    companion object
}

private object EmptyContext : Context {
    override fun <Left : Selectable, Right : Selectable> Left.join(other: Right): JoinStep<Left, Right> {
        return JoinStep(this, other, JoinedTable.JoinType.INNER)
    }

    override fun <T : Selectable> from(source: T) = Filterable(source, emptyList())

    override fun <T : Selectable> T.query() = Filterable(this, emptyList())

    override fun alias(source: Selectable): String? = null
}

val Context.Companion.empty: Context get() = EmptyContext
