package erika.core.linq

class JoinStep<Left : Selectable, Right : Selectable> internal constructor(
    private val left: Left,
    private val right: Right,
    private val joinType: JoinedTable.JoinType
) {

    infix fun on(contract: (left: Left, right: Right) -> Expressible): JoinedTable<Left, Right> {
        return JoinedTable(left, right, joinType, contract(left, right))
    }
}