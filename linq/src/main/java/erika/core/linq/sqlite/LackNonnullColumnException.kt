package erika.core.linq.sqlite

class LackNonnullColumnException(column: Column<*>) :
    IllegalStateException("Insert statement lacks a nonnull column ${column.owner}.${column.name}")