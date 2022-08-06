package erika.core.linq

class BinaryExpression<T> internal constructor(
    private val operator: BinaryOperator,
    private val left: Expressible,
    private val right: Expressible
) : Expression<T> {

    override fun clause(context: Context): String {
        return "(${left.clause(context)})${operator.op}(${right.clause(context)})"
    }

    override fun args(): Array<Any?> {
        val leftArgs = left.args()
        val rightArgs = right.args()
        return leftArgs.plus(elements = rightArgs)
    }
}

internal enum class BinaryOperator(val op: String) {
    LESS_THAN("<"),
    LESS_THAN_OR_EQUALS("<="),
    EQUALS("="),
    GREATER_THAN_OR_EQUALS(">="),
    GREATER_THAN(">"),
    NOT_EQUALS("<>"),
    AND("AND"),
    OR("OR"),
    LIKE("LIKE"),
    BITWISE_OR("|"),
    BITWISE_AND("&"),
    PLUS("+"),
    MULTIPLY("*"),
    MINUS("-"),
    DIV("/"),
    IS("IS"),
    IS_NOT("IS NOT")
}
