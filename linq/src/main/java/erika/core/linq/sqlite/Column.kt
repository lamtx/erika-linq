package erika.core.linq.sqlite

import erika.core.linq.Context
import erika.core.linq.ForeignKey
import erika.core.linq.LiteralExpression
import erika.core.linq.NamedExpression

class Column<T>(
    override val name: String,
    val owner: Table,
    private val sqlType: SqliteType
) : NamedExpression<T> {

    internal var isPrimary = false
    internal var isNonNull = false
    private var autoIncrement = false
    private var defaultValue: T? = null

    internal var foreignKey: ForeignKey<T>? = null
        private set

    fun autoIncrement(): Column<T> {
        autoIncrement = true
        return this
    }

    infix fun references(other: Column<T>): Column<T> {
        foreignKey = ForeignKey(other.owner, other)
        return this
    }

    infix fun default(value: T): Column<T> {
        defaultValue = value
        return this
    }

    fun definition(): String {
        val sb = StringBuilder()
        sb.append(name).append(" ").append(sqlType.name)
        if (isNonNull || isPrimary) {
            sb.append(" NOT NULL")
        }
        if (defaultValue != null) {
            sb.append(" DEFAULT ${LiteralExpression(defaultValue).value}")
        }
        return sb.toString()
    }

    override fun clause(context: Context): String {
        val alias = context.alias(owner)
        return if (alias == null) {
            name
        } else {
            "$alias.$name"
        }
    }

    override fun args(): Array<Any?> = emptyArray()
}

fun <T : Any> Column<out T?>.nonnull(): Column<T> {
    isNonNull = true
    @Suppress("UNCHECKED_CAST")
    return this as Column<T>
}

fun <T : Any> Column<out T?>.primary(): Column<T> {
    isPrimary = true
    @Suppress("UNCHECKED_CAST")
    return this as Column<T>
}
