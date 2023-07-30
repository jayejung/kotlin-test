package asynchronousflow

/*
 representing multiple values
 */
fun main() {
    simple().forEach { value -> println(value) }
}

private fun simple(): List<Int> = listOf(1, 2, 3)