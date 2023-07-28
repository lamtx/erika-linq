package erika.core.linq

class Group<TSource : Expressible, Key>(
    val source: TSource,
    val key: Key
) : Expressible by source

class GroupQueryable<T : Expressible, Key : Any>(
    private val base: Queryable<T>,
    key: Key
) : Queryable<Group<T, Key>> {
    private val groupByClauses = collectExpressible(key)

    override val source: Group<T, Key> = Group(base.source, key)

    override fun args() = base.args()

    override fun clause(context: Context): String {
        val sb = StringBuilder(base.clause(context)).append(" GROUP BY ")
        for (i in groupByClauses.indices) {
            if (i != 0) {
                sb.append(", ")
            }
            sb.append(groupByClauses[i].clause(context))
        }
        return sb.toString()
    }
}
