fun main(args: Array<String>) {
    val a: Int = 7
    val b = 10
    val c: Int = 1
    var d: Int = 0

    println("a: $a, b: $b, c: $c, d: $d")

    val e = 100
    // val f: Long = e /* 자동 형변환 안됨 */
    val f: Long = e.toLong()
    val g: Long = e.toLong() + 1L

    var s: String = "변경 전의 값"
    println(s)
    s = "변경 후의 값"
    println(s)

    val x: Byte = 2
    val y = x + 1L

    val s2 = "삶이 그대를 속일지라도\n슬퍼하거나 노하지 말라\n"

    val s3 = """
        >삶이 그대를 속일지라도
        >슬퍼하거나 노하지 말라
        >슬픈 날엔 참고 견디라
        >즐거운 날이 오고야 말리니
        """.trimMargin(">")
    println(s3)

    val strInt = "77".toInt()
    val strDouble = "123.5".toDouble()
    println("$strInt, $strDouble")

    val alphaNum1: String = "22334457"
    val alphaNum2: String = "1122334456"

    println("alphaNum1.compareTo(alphaNum2): ${alphaNum1.compareTo(alphaNum2)}")
    println("alphaNum1 > alphaNum2: ${alphaNum1.toLong() > alphaNum2.toLong()}")

}