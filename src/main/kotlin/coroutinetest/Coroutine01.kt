package coroutinetest

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    launch {    // launch a new coroutine and continue
        delay(1000L)    // non-blocking delay for 1 second (default time unit is ms)
        println("world!")       // print after delay
    }

    println("Hello")            // main coroutine continues while a previous one is delayed
}