package erika.core.linq

class LimitedSelectable(
    private val source: Selectable,
    private val limit: Int,
    private val offset: Int
) : Selectable by source {

    override fun clause(context: Context): String {
        return source.clause(context) + " LIMIT " + limit + " OFFSET " + offset
    }
}
