package erika.core.linq

interface Selectable : Expressible {
    fun allExpressible(): Array<out Expressible>
}

fun Selectable.limit(limit: Int, offset: Int): Selectable {
    return LimitedSelectable(this, limit, offset)
}

fun Selectable.orderBy(vararg expressible: Expressible) = OrderedSelectable(this, expressible)

infix fun <Left : Selectable, Right : Selectable> Left.join(other: Right) = JoinStep(this, other, JoinedTable.JoinType.INNER)

fun Selectable.leftJoin(other: Selectable) = JoinStep(this, other, JoinedTable.JoinType.LEFT)
