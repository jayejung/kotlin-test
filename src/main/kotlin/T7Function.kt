fun main(args: Array<String>) {
    varargEg()
    println(calcCombination(45, 6))     // 로또 복권의 모든 조합 가능한 번호 개수를 출력

    var retVal: Int? = getNullableReturn(11)
    println(retVal)
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

fun calcCombination(whole: Int, selected: Int): Double {
    if ((selected > whole) || (selected <= 0) || (whole <= 0)) {
        return -1.0
    } else if (selected == whole) {
        return 1.0
    }

    fun calcFactorial(num: Int): Double {
        var total: Double = 1.0
        for (i in num downTo 1) {
            total *= i
        }
        return total
    }

    return calcFactorial(whole) / (calcFactorial(whole - selected) * calcFactorial(selected))
}

fun getNullableReturn(value: Int): Int? {
    if (value >= 10) {
        return null
    } else {
        return value
    }
}