import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    //simple().forEach { value -> println(value) }
    //simple2().forEach { value -> println(value) }

    runBlocking<Unit> {
        simple3().forEach { value ->
            println(value)
        }
    }
}

fun simple(): List<Int> = listOf(1, 2, 3)

fun simple2(): Sequence<Int> = sequence {
    for (i in 1..3) {
        Thread.sleep(500)
        yield(i)
    }
}

suspend fun simple3(): List<Int> {
    delay(5000)  // pretend we are doing somthing asynchronous here
    return listOf(1, 2, 3)
}

