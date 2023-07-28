package erika.core.linq

class JoinedTable<Left : Selectable, Right : Selectable> internal constructor(
    val left: Left,
    val right: Right,
    private val joinType: JoinType,
    private val on: Expressible
) : Selectable {
    operator fun component1(): Left = left

    operator fun component2(): Right = right

    internal enum class JoinType(val op: String) {
        INNER("JOIN"), LEFT("LEFT JOIN"), RIGHT("RIGHT JOIN"), CROSS("CROSS JOIN")
    }

    override fun allExpressible(): Array<out Expressible> {
        val leftExpressible = left.allExpressible()
        val rightExpressible = right.allExpressible()
        return leftExpressible.plus(elements = rightExpressible)
    }

    override fun clause(context: Context) =
        "${left.clause(context)} ${joinType.op} ${right.clause(context)} ON ${on.clause(context)}"

    override fun args(): Array<Any?> = emptyArray()
}

private operator fun <T> Array<out T>.plus(elements: Array<out T>): Array<T> {
    val thisSize = size
    val arraySize = elements.size
    // Using kotlin array copy function actually cause bugs
    @Suppress("ReplaceJavaStaticMethodWithKotlinAnalog")
    val result = java.util.Arrays.copyOf(this, thisSize + arraySize)
    System.arraycopy(elements, 0, result, thisSize, arraySize)
    return result
}
