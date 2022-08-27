package erika.core.linq

import java.util.*

interface Expression<out T> : Expressible

// Equal
@JvmName("eqInt")
infix fun Expression<Int?>.eq(other: Expression<Int?>) = binary(BinaryOperator.EQUALS, other)

@JvmName("eqLong")
infix fun Expression<Long?>.eq(other: Expression<Long?>) = binary(BinaryOperator.EQUALS, other)

@JvmName("eqString")
infix fun Expression<String?>.eq(other: Expression<String?>) = binary(BinaryOperator.EQUALS, other)

@JvmName("eqDouble")
infix fun Expression<Double?>.eq(other: Expression<Double?>) = binary(BinaryOperator.EQUALS, other)

@JvmName("eqFloat")
infix fun Expression<Float?>.eq(other: Expression<Float?>) = binary(BinaryOperator.EQUALS, other)

@JvmName("eqDate")
infix fun Expression<Date?>.eq(other: Expression<Date?>) = binary(BinaryOperator.EQUALS, other)

@JvmName("eqBoolean")
infix fun Expression<Boolean?>.eq(other: Expression<Boolean?>) =
    binary(BinaryOperator.EQUALS, other)

@JvmName("eqEnum")
infix fun <T : Enum<T>> Expression<T?>.eq(other: Expression<T>) =
    binary(BinaryOperator.EQUALS, other)

infix fun Expression<Int?>.eq(value: Int?) =
    if (value == null) binary(BinaryOperator.IS, null) else binary(BinaryOperator.EQUALS, value)

infix fun Expression<Long?>.eq(value: Long?) =
    if (value == null) binary(BinaryOperator.IS, null) else binary(BinaryOperator.EQUALS, value)

infix fun Expression<Double?>.eq(value: Double?) =
    if (value == null) binary(BinaryOperator.IS, null) else binary(BinaryOperator.EQUALS, value)

infix fun Expression<Float?>.eq(value: Float?) =
    if (value == null) binary(BinaryOperator.IS, null) else binary(BinaryOperator.EQUALS, value)

infix fun Expression<Boolean?>.eq(value: Boolean?) =
    if (value == null) binary(BinaryOperator.IS, null) else binary(BinaryOperator.EQUALS, value)

infix fun Expression<String?>.eq(value: String?) =
    if (value == null) binary(BinaryOperator.IS, null) else binary(BinaryOperator.EQUALS, value)

infix fun Expression<Date?>.eq(value: Date?) =
    if (value == null) binary(BinaryOperator.IS, null) else binary(BinaryOperator.EQUALS, value)

infix fun <T : Enum<T>> Expression<T?>.eq(value: T?) =
    if (value == null) binary(BinaryOperator.IS, null) else binary(BinaryOperator.EQUALS, value)

// Not Equal
@JvmName("neInt")
infix fun Expression<Int?>.ne(other: Expression<Int?>) = binary(BinaryOperator.NOT_EQUALS, other)

@JvmName("neLong")
infix fun Expression<Long?>.ne(other: Expression<Long?>) = binary(BinaryOperator.NOT_EQUALS, other)

@JvmName("neString")
infix fun Expression<String?>.ne(other: Expression<String?>) =
    binary(BinaryOperator.NOT_EQUALS, other)

@JvmName("neDouble")
infix fun Expression<Double?>.ne(other: Expression<Double?>) =
    binary(BinaryOperator.NOT_EQUALS, other)

@JvmName("neFloat")
infix fun Expression<Float?>.ne(other: Expression<Float?>) =
    binary(BinaryOperator.NOT_EQUALS, other)

@JvmName("neDate")
infix fun Expression<Date?>.ne(other: Expression<Date?>) = binary(BinaryOperator.NOT_EQUALS, other)

@JvmName("neBoolean")
infix fun Expression<Boolean?>.ne(other: Expression<Boolean?>) =
    binary(BinaryOperator.NOT_EQUALS, other)

@JvmName("neEnum")
infix fun <T : Enum<T>> Expression<T?>.ne(other: Expression<T?>) =
    binary(BinaryOperator.NOT_EQUALS, other)

infix fun Expression<Int?>.ne(value: Int?) =
    if (value == null) binary(BinaryOperator.IS_NOT, null) else binary(BinaryOperator.NOT_EQUALS,
        value)

infix fun Expression<Long?>.ne(value: Long?) =
    if (value == null) binary(BinaryOperator.IS_NOT, null) else binary(BinaryOperator.NOT_EQUALS,
        value)

infix fun Expression<Double?>.ne(value: Double?) =
    if (value == null) binary(BinaryOperator.IS_NOT, null) else binary(BinaryOperator.NOT_EQUALS,
        value)

infix fun Expression<Float?>.ne(value: Float?) =
    if (value == null) binary(BinaryOperator.IS_NOT, null) else binary(BinaryOperator.NOT_EQUALS,
        value)

infix fun Expression<Boolean?>.ne(value: Boolean?) =
    if (value == null) binary(BinaryOperator.IS_NOT, null) else binary(BinaryOperator.NOT_EQUALS,
        value)

infix fun Expression<String?>.ne(value: String?) =
    if (value == null) binary(BinaryOperator.IS_NOT, null) else binary(BinaryOperator.NOT_EQUALS,
        value)

infix fun Expression<Date?>.ne(value: Date?) =
    if (value == null) binary(BinaryOperator.IS_NOT, null) else binary(BinaryOperator.NOT_EQUALS,
        value)

infix fun <T : Enum<T>> Expression<T?>.ne(value: T?) =
    if (value == null) binary(BinaryOperator.IS_NOT, null) else binary(BinaryOperator.NOT_EQUALS,
        value)

// Less than
@JvmName("ltInt")
infix fun Expression<Int?>.lt(other: Expression<Int>) = binary(BinaryOperator.LESS_THAN, other)

@JvmName("ltLong")
infix fun Expression<Long?>.lt(other: Expression<Long?>) = binary(BinaryOperator.LESS_THAN, other)

@JvmName("ltString")
infix fun Expression<String?>.lt(other: Expression<String>) = binary(BinaryOperator.LESS_THAN, other)

@JvmName("ltDouble")
infix fun Expression<Double?>.lt(other: Expression<Double?>) = binary(BinaryOperator.LESS_THAN, other)

@JvmName("ltFloat")
infix fun Expression<Float?>.lt(other: Expression<Float?>) = binary(BinaryOperator.LESS_THAN, other)

@JvmName("ltDate")
infix fun Expression<Date?>.lt(other: Expression<Date?>) = binary(BinaryOperator.LESS_THAN, other)

@JvmName("ltBoolean")
infix fun Expression<Boolean?>.lt(other: Expression<Boolean?>) =
    binary(BinaryOperator.LESS_THAN, other)

@JvmName("ltEnum")
infix fun <T : Enum<T>> Expression<T?>.lt(other: Expression<T?>) =
    binary(BinaryOperator.LESS_THAN, other)

infix fun Expression<Int?>.lt(value: Int) = binary(BinaryOperator.LESS_THAN, value)

infix fun Expression<Long?>.lt(value: Long) = binary(BinaryOperator.LESS_THAN, value)

infix fun Expression<Double?>.lt(value: Double) = binary(BinaryOperator.LESS_THAN, value)

infix fun Expression<Float?>.lt(value: Float) = binary(BinaryOperator.LESS_THAN, value)

infix fun Expression<Boolean?>.lt(value: Boolean) = binary(BinaryOperator.LESS_THAN, value)

infix fun Expression<String?>.lt(value: String) = binary(BinaryOperator.LESS_THAN, value)

infix fun Expression<Date?>.lt(value: Date) = binary(BinaryOperator.LESS_THAN, value)

infix fun <T : Enum<T>> Expression<T?>.lt(value: T) = binary(BinaryOperator.LESS_THAN, value)

// Less than or equal
@JvmName("leInt")
infix fun Expression<Int?>.le(other: Expression<Int?>) =
    binary(BinaryOperator.LESS_THAN_OR_EQUALS, other)

@JvmName("leLong")
infix fun Expression<Long?>.le(other: Expression<Long?>) =
    binary(BinaryOperator.LESS_THAN_OR_EQUALS, other)

@JvmName("leString")
infix fun Expression<String?>.le(other: Expression<String?>) =
    binary(BinaryOperator.LESS_THAN_OR_EQUALS, other)

@JvmName("leDouble")
infix fun Expression<Double?>.le(other: Expression<Double?>) =
    binary(BinaryOperator.LESS_THAN_OR_EQUALS, other)

@JvmName("leFloat")
infix fun Expression<Float?>.le(other: Expression<Float?>) =
    binary(BinaryOperator.LESS_THAN_OR_EQUALS, other)

@JvmName("leDate")
infix fun Expression<Date?>.le(other: Expression<Date?>) =
    binary(BinaryOperator.LESS_THAN_OR_EQUALS, other)

@JvmName("leBoolean")
infix fun Expression<Boolean?>.le(other: Expression<Boolean?>) =
    binary(BinaryOperator.LESS_THAN_OR_EQUALS, other)

@JvmName("leEnum")
infix fun <T : Enum<T>> Expression<T?>.le(other: Expression<T?>) =
    binary(BinaryOperator.LESS_THAN_OR_EQUALS, other)

infix fun Expression<Int>.le(value: Int) = binary(BinaryOperator.LESS_THAN_OR_EQUALS, value)

infix fun Expression<Long>.le(value: Long) = binary(BinaryOperator.LESS_THAN_OR_EQUALS, value)

infix fun Expression<Double>.le(value: Double) = binary(BinaryOperator.LESS_THAN_OR_EQUALS, value)

infix fun Expression<Float>.le(value: Float) = binary(BinaryOperator.LESS_THAN_OR_EQUALS, value)

infix fun Expression<Boolean>.le(value: Boolean) = binary(BinaryOperator.LESS_THAN_OR_EQUALS, value)

infix fun Expression<String>.le(value: String) = binary(BinaryOperator.LESS_THAN_OR_EQUALS, value)

infix fun Expression<Date>.le(value: Date) = binary(BinaryOperator.LESS_THAN_OR_EQUALS, value)

infix fun <T : Enum<T>> Expression<T?>.le(value: T) =
    binary(BinaryOperator.LESS_THAN_OR_EQUALS, value)

// Greater than
@JvmName("gtInt")
infix fun Expression<Int?>.gt(other: Expression<Int?>) = binary(BinaryOperator.GREATER_THAN, other)

@JvmName("gtLong")
infix fun Expression<Long?>.gt(other: Expression<Long?>) = binary(BinaryOperator.GREATER_THAN, other)

@JvmName("gtString")
infix fun Expression<String?>.gt(other: Expression<String?>) =
    binary(BinaryOperator.GREATER_THAN, other)

@JvmName("gtDouble")
infix fun Expression<Double?>.gt(other: Expression<Double?>) =
    binary(BinaryOperator.GREATER_THAN, other)

@JvmName("gtFloat")
infix fun Expression<Float?>.gt(other: Expression<Float?>) =
    binary(BinaryOperator.GREATER_THAN, other)

@JvmName("gtDate")
infix fun Expression<Date?>.gt(other: Expression<Date?>) = binary(BinaryOperator.GREATER_THAN, other)

@JvmName("gtBoolean")
infix fun Expression<Boolean?>.gt(other: Expression<Boolean?>) =
    binary(BinaryOperator.GREATER_THAN, other)

@JvmName("gtEnum")
infix fun <T : Enum<T>> Expression<T?>.gt(other: Expression<T?>) =
    binary(BinaryOperator.GREATER_THAN, other)

infix fun Expression<Int?>.gt(value: Int) = binary(BinaryOperator.GREATER_THAN, value)

infix fun Expression<Long?>.gt(value: Long) = binary(BinaryOperator.GREATER_THAN, value)

infix fun Expression<Double?>.gt(value: Double) = binary(BinaryOperator.GREATER_THAN, value)

infix fun Expression<Float?>.gt(value: Float) = binary(BinaryOperator.GREATER_THAN, value)

infix fun Expression<Boolean?>.gt(value: Boolean) = binary(BinaryOperator.GREATER_THAN, value)

infix fun Expression<String?>.gt(value: String) = binary(BinaryOperator.GREATER_THAN, value)

infix fun Expression<Date?>.gt(value: Date) = binary(BinaryOperator.GREATER_THAN, value)

infix fun <T : Enum<T>> Expression<T?>.gt(value: T) = binary(BinaryOperator.GREATER_THAN, value)

// Greater than or equals
@JvmName("geInt")
infix fun Expression<Int?>.ge(other: Expression<Int>) =
    binary(BinaryOperator.GREATER_THAN_OR_EQUALS, other)

@JvmName("geLong")
infix fun Expression<Long?>.ge(other: Expression<Long>) =
    binary(BinaryOperator.GREATER_THAN_OR_EQUALS, other)

@JvmName("geString")
infix fun Expression<String?>.ge(other: Expression<String>) =
    binary(BinaryOperator.GREATER_THAN_OR_EQUALS, other)

@JvmName("geDouble")
infix fun Expression<Double?>.ge(other: Expression<Double>) =
    binary(BinaryOperator.GREATER_THAN_OR_EQUALS, other)

@JvmName("geFloat")
infix fun Expression<Float?>.ge(other: Expression<Float>) =
    binary(BinaryOperator.GREATER_THAN_OR_EQUALS, other)

@JvmName("geDate")
infix fun Expression<Date?>.ge(other: Expression<Date>) =
    binary(BinaryOperator.GREATER_THAN_OR_EQUALS, other)

@JvmName("geBoolean")
infix fun Expression<Boolean?>.ge(other: Expression<Boolean>) =
    binary(BinaryOperator.GREATER_THAN_OR_EQUALS, other)

@JvmName("geEnum")
infix fun <T : Enum<T>> Expression<T?>.ge(other: Expression<T>) =
    binary(BinaryOperator.GREATER_THAN_OR_EQUALS, other)

infix fun Expression<Int?>.ge(value: Int) = binary(BinaryOperator.GREATER_THAN_OR_EQUALS, value)

infix fun Expression<Long?>.ge(value: Long) = binary(BinaryOperator.GREATER_THAN_OR_EQUALS, value)

infix fun Expression<Double?>.ge(value: Double) =
    binary(BinaryOperator.GREATER_THAN_OR_EQUALS, value)

infix fun Expression<Float?>.ge(value: Float) = binary(BinaryOperator.GREATER_THAN_OR_EQUALS, value)

infix fun Expression<Boolean?>.ge(value: Boolean) =
    binary(BinaryOperator.GREATER_THAN_OR_EQUALS, value)

infix fun Expression<String?>.ge(value: String) =
    binary(BinaryOperator.GREATER_THAN_OR_EQUALS, value)

infix fun Expression<Date?>.ge(value: Date) = binary(BinaryOperator.GREATER_THAN_OR_EQUALS, value)

infix fun <T : Enum<T>> Expression<T?>.ge(value: T) =
    binary(BinaryOperator.GREATER_THAN_OR_EQUALS, value)

// end of comparison

infix fun Expression<String?>.like(other: Expression<String>) = binary(BinaryOperator.LIKE, other)

infix fun Expression<String?>.like(value: String) = binary(BinaryOperator.LIKE, value)

infix fun Expression<Boolean>.or(value: Boolean): Expression<Boolean> =
    if (value) LiteralExpression(true) else this

infix fun Expression<Boolean?>.or(other: Expression<Boolean>) = binary(BinaryOperator.OR, other)

infix fun Expression<Boolean>.and(value: Boolean): Expression<Boolean> =
    if (value) this else LiteralExpression(false)

infix fun Expression<Boolean?>.and(other: Expression<Boolean>) = binary(BinaryOperator.AND, other)

infix fun <T : Number> Expression<T?>.bitwiseOr(other: Expression<T>) =
    BinaryExpression<T>(BinaryOperator.BITWISE_OR, this, other)

infix fun <T : Number> Expression<T?>.bitwiseOr(value: Long) =
    BinaryExpression<T>(BinaryOperator.BITWISE_OR, this, LiteralExpression(value))

infix fun <T : Number> Expression<T?>.bitwiseAnd(other: Expression<T>) =
    BinaryExpression<T>(BinaryOperator.BITWISE_AND, this, other)

infix fun <T : Number> Expression<T?>.bitwiseAnd(value: Long) =
    BinaryExpression<T>(BinaryOperator.BITWISE_AND, this, LiteralExpression(value))

fun Expression<String?>.contains(value: String?) =
    binary(BinaryOperator.LIKE, ContainExpression(LiteralExpression(value)))

fun <T> Expression<T>.desc(): Expression<T> = postfix("DESC")

fun <T> Expression<T>.asc(): Expression<T> = postfix("ASC")

fun <T> Expression<T>.max(): Expression<T?> = func("max")

fun <T> Expression<T>.count(): Expression<Int> = func("count")

fun <T> Expression<T>.sum(): Expression<T> = func("sum")

fun <T> Expression<T>.min(): Expression<T?> = func("min")

fun Expression<String?>.groupConcat(separator: String) =
    func<String?, String?>("group_concat", separator)

fun <T, R> Expression<T>.func(functionName: String): Expression<R> =
    FunctionExpression(functionName, arrayOf(this))

fun <T, R> Expression<T>.func(functionName: String, otherArg: Any?): Expression<R> =
    FunctionExpression(functionName, arrayOf(this, LiteralExpression(otherArg)))

fun <T, R> Expression<T>.postfix(functionName: String): Expression<R> =
    PostfixExpression(functionName, this)

operator fun <T> Expression<T>.plus(other: Expression<T>) = arithmetic(BinaryOperator.PLUS, other)

operator fun <T> Expression<T>.plus(value: T): Expression<T> =
    arithmetic(BinaryOperator.PLUS, value)

operator fun <T> Expression<T>.times(other: Expression<T>) =
    arithmetic(BinaryOperator.MULTIPLY, other)

operator fun <T> Expression<T>.times(value: T): Expression<T> =
    arithmetic(BinaryOperator.MULTIPLY, value)

operator fun <T> Expression<T>.minus(other: Expression<T>) = arithmetic(BinaryOperator.MINUS, other)

operator fun <T> Expression<T>.minus(value: T) = arithmetic(BinaryOperator.MINUS, value)

operator fun <T> Expression<T>.div(other: Expression<T>) = arithmetic(BinaryOperator.DIV, other)

operator fun <T> Expression<T>.div(value: T) = arithmetic(BinaryOperator.DIV, value)

private fun <T> Expression<T>.binary(op: BinaryOperator, value: T): Expression<Boolean> =
    BinaryExpression(op, this, LiteralExpression(value))

private fun <T> Expression<T>.binary(
    op: BinaryOperator,
    other: Expression<T>,
): Expression<Boolean> = BinaryExpression(op, this, other)

private fun <T> Expression<T>.arithmetic(op: BinaryOperator, value: T): Expression<T> =
    BinaryExpression(op, this, LiteralExpression(value))

private fun <T> Expression<T>.arithmetic(op: BinaryOperator, other: Expression<T>): Expression<T> =
    BinaryExpression(op, this, other)

private class PostfixExpression<T>(
    val functionName: String,
    val arg: Expression<*>,
) : Expression<T> {

    override fun args() = arg.args()

    override fun clause(context: Context) = "${arg.clause(context)} $functionName"
}

private class FunctionExpression<T>(
    private val func: String,
    private val args: Array<out Expression<*>>,
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

private class ContainExpression(val arg: Expression<String?>) : Expression<String> {

    override fun args() = arg.args()

    override fun clause(context: Context) = "'%' || ${arg.clause(context)} || '%'"
}