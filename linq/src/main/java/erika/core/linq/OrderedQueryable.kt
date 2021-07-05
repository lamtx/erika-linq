package erika.core.linq

class OrderedQueryable<T : Expressible> internal constructor(
        private val base: Queryable<T>,
        private val orderBy: Array<out Expressible>
) : Queryable<T> by base {

    override fun clause(context: Context): String {
        val sb = StringBuilder(base.clause(context))
        sb.append(" ORDER BY ")
        for (i in orderBy.indices) {
            if (i != 0) {
                sb.append(", ")
            }
            sb.append(orderBy[i].clause(context))
        }
        return sb.toString()
    }
}
