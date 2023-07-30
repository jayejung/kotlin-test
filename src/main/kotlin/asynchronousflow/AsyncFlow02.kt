package asynchronousflow

/*
 Sequences
 */

fun main() {
    simple().forEach { value -> println(value) }
}

private fun simple(): Sequence<Int> = sequence {// sequence builder
    for (i in 1..3) {
        Thread.sleep(1000)   // pretend we are computing it
        yield(i)    // yield next value
    }
}