package erika.core.linq.sqlite

class InsertNullValueException(column: Column<*>) : IllegalStateException("Insert or update a null value to a nonnull column  ${column.owner.tableName}.${column.name}")