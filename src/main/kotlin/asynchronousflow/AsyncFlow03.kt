package asynchronousflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/*
 Suspending functions
 */

fun main() = runBlocking<Unit> {
    simple().forEach { value -> println(value) }
}

private suspend fun simple(): List<Int> {
    delay(1000)
    return listOf(1, 2, 3)
}