fun main(args: Array<String>) {
    varargEg()
}

fun varargEg() {
    val e = arrayOf(7, 8, 9)
    val list = newList(1, 2, 3, *e, 4, 5)
    println(list)

    val list2 = listOf(*e, 1, 2, 3)
    println(list2)

    val array = arrayOf(0, *e, 1, 2)
    println(array.toList())
}

fun <T> newList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts)
        result.add(t)
    return result
}