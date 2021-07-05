package erika.core.linq

interface Queryable<T> : Expression<T> {
    val source: T
}

fun <T : Expressible> Queryable<T>.limit(limit: Int, offset: Int = 0) = LimitedQueryable(this, limit, offset)

fun <T : Expressible, Key : Any> Queryable<T>.groupBy(selector: (T) -> Key) = GroupQueryable(this, selector(source))

fun <T : Expressible> Queryable<T>.orderBy(block: ((T) -> Array<out Expressible>)) = OrderedQueryable(this, block(source))

fun <T : Expressible> Queryable<T>.selectAll(): Collectible<T> {
    return Collectible(
            distinct = false,
            source = source,
            baseSource = source,
            query = this
    )
}

fun <T : Expressible> Queryable<T>.selectAllDistinct(): Collectible<T> {
    return Collectible(
            distinct = true,
            source = source,
            baseSource = source,
            query = this
    )
}

fun <T : Expressible, R : Any> Queryable<T>.select(selector: (T) -> R): Collectible<R> {
    return Collectible(
            distinct = false,
            source = selector(source),
            baseSource = source,
            query = this
    )
}

fun <T : Expressible, R : Any> Queryable<T>.selectDistinct(selector: (T) -> R): Collectible<R> {
    return Collectible(
            distinct = true,
            source = selector(source),
            baseSource = source,
            query = this
    )
}