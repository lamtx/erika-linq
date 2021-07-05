package erika.core.linq

interface Expression<out T> : Expressible

infix fun <T> Expression<T>.eq(other: Expression<T>) = binary(BinaryOperator.EQUALS, other)

infix fun <T> Expression<T>.eq(value: T) = if (value == null) binary(BinaryOperator.IS, null) else binary(BinaryOperator.EQUALS, value)

infix fun <T> Expression<T>.ne(value: T) = if (value == null) binary(BinaryOperator.IS_NOT, null) else binary(BinaryOperator.NOT_EQUALS, value)

infix fun <T> Expression<T>.lt(value: T) = binary(BinaryOperator.LESS_THAN, value)

infix fun <T> Expression<T>.le(value: T) = binary(BinaryOperator.LESS_THAN_OR_EQUALS, value)

infix fun <T> Expression<T>.le(other: Expression<T>) = binary(BinaryOperator.LESS_THAN_OR_EQUALS, other)

infix fun <T> Expression<T>.ge(value: T) = binary(BinaryOperator.GREATER_OR_EQUALS, value)

infix fun <T> Expression<T>.gt(value: T) = binary(BinaryOperator.GREATER_THAN, value)

infix fun <T> Expression<T>.like(other: Expression<T>) = binary(BinaryOperator.LIKE, other)

infix fun <T> Expression<T>.like(value: T) = binary(BinaryOperator.LIKE, value)

infix fun Expression<Boolean>.or(value: Boolean) = if (value) LiteralExpression(true) else this

infix fun Expression<Boolean>.or(other: Expression<Boolean>) = binary(BinaryOperator.OR, other)

infix fun Expression<Boolean>.and(value: Boolean) = if (value) this else LiteralExpression(false)

infix fun Expression<Boolean>.and(other: Expression<Boolean>) = binary(BinaryOperator.AND, other)

infix fun <T : Number> Expression<T>.bitwiseOr(other: Expression<T>) = BinaryExpression<T>(BinaryOperator.BITWISE_OR, this, other)

infix fun <T : Number> Expression<T>.bitwiseOr(value: Long) = BinaryExpression<T>(BinaryOperator.BITWISE_OR, this, LiteralExpression(value))

infix fun <T : Number> Expression<T>.bitwiseAnd(other: Expression<T>) = BinaryExpression<T>(BinaryOperator.BITWISE_AND, this, other)

infix fun <T : Number> Expression<T>.bitwiseAnd(value: Long) = BinaryExpression<T>(BinaryOperator.BITWISE_AND, this, LiteralExpression(value))

fun Expression<String?>.contains(value: String?) = binary(BinaryOperator.LIKE, ContainExpression(LiteralExpression(value)))

fun <T> Expression<T>.desc() = postfix<T, T>("DESC")

fun <T> Expression<T>.asc() = postfix<T, T>("ASC")

fun <T> Expression<T>.max() = func<T, T?>("max")

fun <T> Expression<T>.count() = func<T, Int>("count")

fun <T> Expression<T>.sum() = func<T, T>("sum")

fun <T> Expression<T>.min() = func<T, T?>("min")

fun Expression<String?>.groupConcat(separator: String) = func<String?, String?>("group_concat", separator)

fun <T, R> Expression<T>.func(functionName: String): Expression<R> =
        FunctionExpression(functionName, arrayOf(this))

fun <T, R> Expression<T>.func(functionName: String, otherArg: Any?): Expression<R> =
        FunctionExpression(functionName, arrayOf(this, LiteralExpression(otherArg)))

fun <T, R> Expression<T>.postfix(functionName: String): Expression<R> =
        PostfixExpression(functionName, this)

operator fun <T> Expression<T>.plus(other: Expression<T>) = arithmetic(BinaryOperator.PLUS, other)

operator fun <T> Expression<T>.plus(value: T): Expression<T> = arithmetic(BinaryOperator.PLUS, value)

operator fun <T> Expression<T>.times(other: Expression<T>) = arithmetic(BinaryOperator.MULTIPLY, other)

operator fun <T> Expression<T>.times(value: T): Expression<T> = arithmetic(BinaryOperator.MULTIPLY, value)

operator fun <T> Expression<T>.minus(other: Expression<T>) = arithmetic(BinaryOperator.MINUS, other)

operator fun <T> Expression<T>.minus(value: T) = arithmetic(BinaryOperator.MINUS, value)

operator fun <T> Expression<T>.div(other: Expression<T>) = arithmetic(BinaryOperator.DIV, other)

operator fun <T> Expression<T>.div(value: T) = arithmetic(BinaryOperator.DIV, value)

private fun <T> Expression<T>.binary(op: BinaryOperator, value: T): Expression<Boolean> =
        BinaryExpression(op, this, LiteralExpression(value))

private fun <T> Expression<T>.binary(
        op: BinaryOperator,
        other: Expression<T>
): Expression<Boolean> = BinaryExpression(op, this, other)

private fun <T> Expression<T>.arithmetic(op: BinaryOperator, value: T): Expression<T> = BinaryExpression(op, this, LiteralExpression(value))

private fun <T> Expression<T>.arithmetic(op: BinaryOperator, other: Expression<T>): Expression<T> = BinaryExpression(op, this, other)

private class PostfixExpression<T>(
        val functionName: String,
        val arg: Expression<*>
) : Expression<T> {

    override fun args() = arg.args()

    override fun clause(context: Context) = "${arg.clause(context)} $functionName"
}

private class FunctionExpression<T> (
        private val func: String,
        private val args: Array<out Expression<*>>
) : Expression<T> {

    override fun args(): Array<Any?> {
        if (args.size == 1) {
            return args[0].args()
        }
        val list = mutableListOf<Any?>()
        for (arg in args) {
            list.addAll(arg.args())
        }
        return list.toTypedArray()
    }

    override fun clause(context: Context): String {
        val params = if (args.size == 1) {
            args[0].clause(context)
        } else {
            args.joinToString(",", transform = { it.clause(context) })
        }
        return "$func($params)"
    }
}

private class ContainExpression(val arg: Expression<String?>) : Expression<Boolean> {

    override fun args() = arg.args()

    override fun clause(context: Context) = "'%' || ${arg.clause(context)} || '%'"
}