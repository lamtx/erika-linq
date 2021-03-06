package erika.core.linq

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import erika.core.linq.sqlite.*
import org.junit.Test

class ExampleUnitTest {
    object Customers : Table("Customers") {
        val id = int("id").primary()
        val name = text("name").nonnull()
        val age = int("age")
    }

    class AppData(context: Context) : SQLiteOpenHelper(context, "data", null, 1) {
        val customers = CustomerDataContext(this)

        override fun onCreate(db: SQLiteDatabase) {
            db create Customers
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        }

    }

    class CustomerDataContext(dataContext: AppData) : SqlHelper by SqlHelper(dataContext) {
        fun update(id: Long, name: String? = null, age: Int? = null) {
            newContext {
                from(Customers)
                    .where { it.id eq id }
                    .update {
                        if (name != null) {
                            it.name set name
                        }
                        it.age set age
                    }
            }
        }
    }

    @Test
    fun addition_isCorrect() {
    }
}