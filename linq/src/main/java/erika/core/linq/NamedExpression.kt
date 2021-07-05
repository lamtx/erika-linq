package erika.core.linq

import kotlin.reflect.KProperty

interface NamedExpression<out T> : Expression<T>, NamedExpressible

@Suppress("FUNCTIONNAME")
fun <T> NamedExpression(name: String, base: Expression<T>? = null): NamedExpression<T> {
    return object : NamedExpression<T> {
        override fun clause(context: Context): String {
            if (base == null) {
                return name
            }
            return "${base.clause(context)} as $name"
        }

        override fun args(): Array<Any?> {
            return base?.args() ?: emptyArray()
        }

        override val name = name
    }
}

infix fun <T> Expression<T>.named(name: String): NamedExpression<T> {
    return NamedExpression(name, this)
}

operator fun <T> Expression<T>.getValue(any: Any?, property: KProperty<*>): NamedExpression<T> {
    return NamedExpression(property.name, this)
}
