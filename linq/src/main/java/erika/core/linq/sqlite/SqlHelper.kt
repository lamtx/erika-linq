package erika.core.linq.sqlite

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import erika.core.linq.*

interface SqlHelper {
    val readableDatabase: SQLiteDatabase

    val writableDatabase: SQLiteDatabase

    fun <T : Selectable> Filterable<T>.delete(): Int {
        return delete(writableDatabase)
    }

    fun <T : Table> Filterable<T>.update(settersBuilder: SetterScope.(T) -> Unit) {
        return update(writableDatabase) { source ->
            SetterScopeImpl(source).buildWith(settersBuilder)
        }
    }

    fun <T : Table> T.insert(settersBuilder: SetterScope.(T) -> Unit): Long {
        return insert(writableDatabase) { source ->
            SetterScopeImpl(source).buildWith(settersBuilder)
        }
    }

    fun <T : Table> from(table: T) = Context.empty.from(table)
}

@Suppress("FUNCTIONNAME")
fun SqlHelper(database: SQLiteOpenHelper): SqlHelper {
    return object : SqlHelper {
        override val readableDatabase: SQLiteDatabase
            get() = database.readableDatabase
        override val writableDatabase: SQLiteDatabase
            get() = database.writableDatabase
    }
}

private class SetterScopeImpl<TSource : Table>(
    val source: TSource,
) : SetterScope {
    val setters = mutableListOf<Setter<*>>()

    override fun <T> Column<T>.set(value: T) {
        setters.add(Setter(this, value))
    }

    override fun <T> Column<T>.set(value: Expression<T>) {
        setters.add(Setter(this, value))
    }

    fun buildWith(builder: SetterScope.(TSource) -> Unit): List<Setter<*>> {
        builder(source)
        return setters
    }
}

inline fun <T> SqlHelper.newContext(statement: SqlContext.() -> T): T {
    return statement(SqlContext(this))
}

infix fun SQLiteDatabase.create(table: Table) {
    execSQL(table.createStatement())
}

infix fun SQLiteDatabase.add(column: Column<*>) {
    execSQL("ALTER TABLE ${column.owner} ADD COLUMN ${column.definition()}")
}
