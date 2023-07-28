package erika.core.linq.sqlite

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import erika.core.linq.*

class SqlContext(private val sqlHelper: SqlHelper) : Context {
    private val mapping = mutableListOf<Selectable>()

    override fun <T : Selectable> from(source: T): Filterable<T> {
        mapping.add(source)
        return Filterable(source, emptyList())
    }

    override fun <T : Selectable> T.query() = from(this)

    override infix fun <Left : Selectable, Right : Selectable> Left.join(other: Right): JoinStep<Left, Right> {
        mapping.add(this)
        mapping.add(other)
        return JoinStep(this, other, JoinedTable.JoinType.INNER)
    }

    override fun alias(source: Selectable): String? {
        val index = mapping.indexOfFirst { it == source }
        return if (index == -1) null else "t$index"
    }

    private fun fetch(expressible: Expressible, database: SQLiteDatabase): Cursor {
        val query = expressible.clause(this)
        val typedArgs = expressible.args()
        val args = Array(typedArgs.size) { typedArgs[it].toString() }
        Log.d(tag, "Query: $query")
        Log.d(tag, "Args: ${args.contentToString()}")
        return database.rawQuery(query, args)
    }

    fun Collectible<*>.fetchCursor(): Cursor {
        return fetch(this, sqlHelper.readableDatabase)
    }

    fun <T : Any, R> Collectible<T>.collect(creator: (Collector<T>) -> R): MutableList<R> {
        val cursor = fetch(this, sqlHelper.readableDatabase)
        return toList(cursor, source, creator)
    }

    fun <T : Any, R> Collectible<T>.firstOrNull(creator: (Collector<T>) -> R): R? {
        val cursor = fetch(this, sqlHelper.readableDatabase)
        return firstOrNull(cursor, source, creator)
    }

    fun <T : Any, R> Collectible<T>.first(creator: (Collector<T>) -> R): R {
        return firstOrNull(creator) ?: throw NoSuchElementException()
    }

    fun Collectible<*>.exists(): Boolean {
        val cursor = fetch(this, sqlHelper.readableDatabase)
        return exists(cursor)
    }

    val star: Expression<Unit>
        get() = NamedExpression("*")

    val none: Expression<Unit>
        get() = NamedExpression("1")

    companion object {
        const val tag = "SqlContext"
        fun <T, R> toList(cursor: Cursor, source: T, creator: (Collector<T>) -> R): MutableList<R> {
            cursor.use {
                val result = mutableListOf<R>()
                if (cursor.moveToFirst()) {
                    val collector = Collector(source = source, cursor = cursor)
                    do {
                        val obj = creator(collector)
                        result.add(obj)
                        collector.allSet()
                    } while (cursor.moveToNext())
                }
                return result
            }
        }

        fun exists(cursor: Cursor): Boolean {
            cursor.use {
                return cursor.moveToFirst()
            }
        }

        fun <T, R> firstOrNull(cursor: Cursor, table: T, creator: (Collector<T>) -> R): R? {
            cursor.use {
                if (cursor.moveToFirst()) {
                    return creator(Collector(table, cursor))
                }
                return null
            }
        }
    }
}

private fun <T : Expressible> Filterable<T>.buildWhereClause(context: Context): String {
    val clause = clause(context)
    val exclude = "WHERE "
    return if (clause.startsWith(exclude)) {
        clause.substring(exclude.length, clause.length)
    } else {
        clause
    }
}

fun <T : Selectable> Filterable<T>.delete(database: SQLiteDatabase): Int {
    val context = Context.empty
    val args = args()
    val stringArgs = Array(args.size) { args[it].toString() }
    return database.delete(source.clause(context), buildWhereClause(context), stringArgs)
}

fun <T : Table> Filterable<T>.update(database: SQLiteDatabase, setters: (T) -> List<Setter<*>>) {
    val args = args()
    val context = Context.empty
    val contentValues = ContentValues()
    val values = setters(source)
    if (values.all { it.value is LiteralExpression<*> }) {
        val stringArgs = Array(args.size) { args[it].toString() }
        for ((column, value) in values) {
            putToContentValues(contentValues, column, (value as LiteralExpression<*>))
        }

        val whereClause = buildWhereClause(context)
        Log.d(
            SqlContext.tag,
            "Update: $contentValues FROM ${source.clause(context)} WHERE $whereClause"
        )
        Log.d(SqlContext.tag, "Args: ${stringArgs.contentToString()}")
        database.update(source.clause(context), contentValues, whereClause, stringArgs)
    } else {
        val valueStatement = StringBuilder()
        val bindArgs = mutableListOf<Any?>()
        for ((column, value) in values) {
            if (valueStatement.isNotEmpty()) {
                valueStatement.append(", ")
            }
            valueStatement.append(column.name).append("=").append(value.clause(context))
            value.args().let {
                if (it.isNotEmpty()) {
                    bindArgs.addAll(it)
                }
            }
        }
        bindArgs.addAll(args)
        val query = "UPDATE ${source.clause(context)} SET $valueStatement ${clause(context)}"
        val typedArgs = bindArgs.toTypedArray()

        Log.d(SqlContext.tag, "Update: $query")
        Log.d(SqlContext.tag, "Args: ${typedArgs.contentToString()}")
        database.execSQL(query, typedArgs)
    }
}