package erika.core.linq

interface Collectible<T : Any> : Selectable {
    val source: T
}

@Suppress("FUNCTIONNAME")
internal fun <T : Any> Collectible(
    source: T,
    baseSource: Expressible,
    distinct: Boolean,
    query: Expressible
): Collectible<T> = object : Collectible<T> {
    override val source: T = source

    private val allExpressible = collectExpressible(source)

    override fun allExpressible() = allExpressible

    override fun clause(context: Context): String {
        val selections = allExpressible()
        check(selections.isNotEmpty()) { "No selection" }
        return buildString {
            append("SELECT ")
            if (distinct) {
                append("DISTINCT ")
            }
            append(selections.joinToString(separator = ", ", transform = { it.clause(context) }))
            append(" FROM (")
            append(baseSource.clause(context))
            append(")")
            val whereClause = query.clause(context)
            if (whereClause.isNotEmpty()) {
                append(" ")
                append(whereClause)
            }
        }
    }

    override fun args(): Array<Any?> {
        val args = mutableListOf<Any?>()
        for (selection in allExpressible) {
            selection.args().let {
                if (it.isNotEmpty()) {
                    args.addAll(it)
                }
            }
        }
        baseSource.args().let {
            if (it.isNotEmpty()) {
                args.addAll(it)
            }
        }
        query.args().let {
            if (it.isNotEmpty()) {
                args.addAll(it)
            }
        }
        return args.toTypedArray()
    }
}

internal fun collectExpressible(t: Any): Array<out Expressible> {
    if (t is Selectable) {
        return t.allExpressible()
    }
    if (t is Expressible) {
        return arrayOf(t)
    }
    val clazz = t::class.java
    val fields = clazz.declaredFields.mapNotNull { field ->
        field.isAccessible = true
        val value = field.get(t)
        if (value is Expression<*>) value else null
    }
    return fields.toTypedArray()
}