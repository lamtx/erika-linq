package erika.core.linq

class OrderedSelectable(
        private val source: Selectable,
        private val orderBy: Array<out Expressible>) : Selectable by source {

    override fun clause(context: Context): String {
        val sb = StringBuilder(source.clause(context))
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
