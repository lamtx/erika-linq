package erika.core.linq

interface Expressible {

    fun clause(context: Context): String

    fun args(): Array<Any?>

}
