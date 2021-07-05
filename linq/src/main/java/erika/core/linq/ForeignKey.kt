package erika.core.linq

import erika.core.linq.sqlite.Column
import erika.core.linq.sqlite.Table

class ForeignKey<T>(
        val otherTable: Table,
        val otherColumn: Column<T>,
        val otherTableName: String = otherTable.clause(Context.empty)
)