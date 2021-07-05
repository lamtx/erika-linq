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

    fun <T : Table> Filterable<T>.update(setters: (T) -> Array<Setter<*>>) {
        return update(writableDatabase, setters)
    }

    fun <T : Table> T.insert(setters: (T) -> Array<Setter<*>>): Long {
        return insert(writableDatabase, setters)
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

inline fun <T> SqlHelper.newContext(statement: SqlContext.() -> T): T {
    return statement(SqlContext(this))
}

infix fun SQLiteDatabase.create(table: Table) {
    execSQL(table.createStatement())
}

infix fun SQLiteDatabase.add(column: Column<*>) {
    execSQL("ALTER TABLE ${column.owner} ADD COLUMN ${column.definition()}")
}
