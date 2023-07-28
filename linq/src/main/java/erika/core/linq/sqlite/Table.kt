package erika.core.linq.sqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import erika.core.linq.Context
import erika.core.linq.Expressible
import erika.core.linq.LiteralExpression
import erika.core.linq.Selectable
import erika.core.linq.Setter
import erika.core.linq.putToContentValues
import kotlinx.datetime.Instant
import java.util.Date

open class Table(internal val tableName: String) : Selectable {

    private val columns = mutableListOf<Column<*>>()

    protected fun int(name: String): Column<Int?> = register(name, SqliteType.INTEGER)

    protected fun text(name: String): Column<String?> = register(name, SqliteType.TEXT)

    protected fun long(name: String): Column<Long?> = register(name, SqliteType.INTEGER)

    protected fun date(name: String): Column<Date?> = register(name, SqliteType.INTEGER)

    protected fun instant(name: String): Column<Instant?> = register(name, SqliteType.INTEGER)

    protected fun bool(name: String): Column<Boolean?> = register(name, SqliteType.INTEGER)

    protected fun real(name: String): Column<Double?> = register(name, SqliteType.REAL)

    protected fun blob(name: String): Column<ByteArray?> = register(name, SqliteType.BLOB)

    protected fun <T : Enum<T>> enum(name: String): Column<T?> = register(name, SqliteType.INTEGER)

    private fun <T> register(name: String, type: SqliteType): Column<T> {
        return Column<T>(name, this, type).also {
            columns.add(it)
        }
    }

    override fun clause(context: Context): String {
        val alias = context.alias(this)
        return if (alias == null) {
            tableName
        } else {
            "$tableName as $alias"
        }
    }

    override fun args(): Array<Any?> = emptyArray()

    override fun allExpressible(): Array<out Expressible> {
        return columns.toTypedArray()
    }

    override fun toString(): String {
        return tableName
    }

    internal fun createStatement(): String {
        val sb = StringBuilder()
        sb.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (")
        var first = true
        for (column in columns) {
            if (first) {
                first = false
            } else {
                sb.append(",")
            }
            sb.append("\r\n")
            sb.append(column.definition())
        }
        val primaries = columns.filter { it.isPrimary }
        if (primaries.isNotEmpty()) {
            sb.append(",\r\n PRIMARY KEY (")
            var f = true
            for (primary in primaries) {
                if (f) {
                    f = false
                } else {
                    sb.append(",")
                }
                sb.append(primary.name)
            }
            sb.append(")")
        }
        val foreignKeys = collect()
        for (foreign in foreignKeys) {
            sb.append(",\r\nFOREIGN KEY (")
            var f = true
            for (column in foreign.thisKeys) {
                if (f) {
                    f = false
                } else {
                    sb.append(",")
                }
                sb.append(column.name)
            }
            sb.append(") REFERENCES ").append(foreign.foreignTable.tableName).append("(")
            f = true
            for (column in foreign.foreignKeys) {
                if (f) {
                    f = false
                } else {
                    sb.append(",")
                }
                sb.append(column.name)
            }
            sb.append(")")
        }
        sb.append(")")
        return sb.toString()
    }

    private fun collect(): List<InternalForeignKey> {
        val foreignColumn = columns.filter { it.foreignKey != null }

        val internalForeignKeys = ArrayList<InternalForeignKey>()
        for (col in foreignColumn) {
            val name = col.foreignKey!!.otherTableName
            var key = internalForeignKeys.firstOrNull { x -> x.foreignTable.tableName == name }
            if (key == null) {
                val foreignTable = col.foreignKey!!.otherTable
                key = InternalForeignKey(foreignTable)
                key.thisKeys.add(col)
                key.foreignKeys.add(col.foreignKey!!.otherColumn)
                internalForeignKeys.add(key)
            } else {
                key.thisKeys.add(col)
                key.foreignKeys.add(col.foreignKey!!.otherColumn)
            }
        }
        return internalForeignKeys
    }

    private class InternalForeignKey(val foreignTable: Table) {
        val thisKeys = mutableListOf<Column<*>>()
        val foreignKeys = mutableListOf<Column<*>>()
    }

    internal fun allNonnullColumns(): List<Column<*>> {
        return columns.filter { it.isNonNull }
    }
}

fun <T : Table> T.insert(database: SQLiteDatabase, setters: (T) -> List<Setter<*>>): Long {
    val values = ContentValues()
    val columns = setters(this)
    val nonnullColumns = allNonnullColumns()
    for (column in nonnullColumns) {
        if (!columns.any { it.column === column }) {
            throw LackNonnullColumnException(column)
        }
    }
    for ((column, value) in columns) {
        putToContentValues(values, column, value as LiteralExpression<*>)
    }

    database.beginTransaction()
    try {
        return database.insert(tableName, null, values).apply {
            database.setTransactionSuccessful()
        }
    } finally {
        database.endTransaction()
    }
}