package erika.core.linq

class LimitedQueryable<T : Expressible> internal constructor(
        private val base: Queryable<T>,
        private val limit: Int,
        private val offset: Int
) : Queryable<T> by base {
    override fun clause(context: Context): String {
        return base.clause(context) + " LIMIT " + limit + " OFFSET " + offset
    }
}