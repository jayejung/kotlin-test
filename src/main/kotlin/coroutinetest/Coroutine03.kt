package coroutinetest

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// coroutineScope

fun main() = runBlocking { doWorld() }

private suspend fun doWorld() = coroutineScope { // this: CoroutineScope
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello")
}