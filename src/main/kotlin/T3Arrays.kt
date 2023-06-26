fun main(args: Array<String>) {
    var items = arrayOf("사과", "바나나", "키위")

    for (fruit in items) {
        println(fruit)
    }

    val nums = Array<String>(5, { i -> (i * i).toString() })

    for (item in nums) {
        println(item)
    }

    val nums2 = Array<String>(5) { i ->
        (i * i).toString()
    }

    for (item in nums2) {
        println(item)
    }

    val intItem: IntArray = intArrayOf(1, 2, 3, 4, 5)
    val intNums = IntArray(5, { i -> (i * i) })
    val intNums2 = IntArray(5) { i -> (i * i) }

}