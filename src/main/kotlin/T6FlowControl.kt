fun main(args: Array<String>) {
    val result: Unit = ifElseEg(1)
    println("Unit: $Unit")

    whenEg(2, 50, "San Francisco")

    forEg()

    whileEg()
}

fun ifElseEg(param: Int) {
    var result = if (param == 1) {
        "one"
    } else if (param == 2) {
        "two"
    } else {
        "three"
    }
    println("param: $param")
    println("result: $result")
}

fun whenEg(inputType: Int, score: Int, city: String) {
    //---------- #1
    when (inputType) {
        1 -> println("type-1")
        2, 3 -> println("type-2, 3")
        else -> {
            println("undefined")
        }
    }

    //---------- #2
    when (inputType) {
        checkInputType(inputType) -> {
            println("normal type")
        }

        else -> println("abnormal type")
    }

    //---------- #3
    val start = 0
    val end = 100
    when (score) {
        in start..(end / 4) -> println("excellent")
        50 -> println("average")
        in start..end -> println("in reasonable range")
        else -> println("out of range")
    }

    //---------- #4
    val isSanFransisco = when (city) {
        is String -> city.startsWith("San Fransisco")
        else -> false
    }
    println(isSanFransisco)

    //---------- #5
    when {
        city.length == 0 -> println("input a name of city")
        city.substring(0, 13).equals("San Francisco") -> println("This is San Fransisco !!")
        else -> println(city)
    }
}

fun checkInputType(inputType: Int): Int {
    if (inputType >= 1 && inputType < 3) {
        return inputType
    }
    return -1
}

fun forEg() {
    // ------------> #1
    val item1 = listOf("apple", "banana", "kiwi")
    for (item in item1) {
        println(item)
    }

    // ------------> #2
    val item2 = listOf("apple", "banana", "kiwi")
    for (index in item2.indices) {
        println("item a $index is ${item2[index]}")
    }

    // ------------> #3
    val item3 = arrayOf("apple", "banana", "kiwi")
    for (index in item3.indices) {
        println("item at $index is ${item3[index]}")
    }
}

fun whileEg() {
    // ----------> while
    val items = listOf("apple", "banana", "kiwi")
    var index = 0
    while (index < items.size) {
        println("item at $index is ${items[index]}")
        index++
    }

    // ---------> do - while
    index = 0
    do {
        println("item at $index is ${items[index]}")
        index++
    } while (index < items.size)
}