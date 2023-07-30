package coroutinetest

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
 Coroutines are light-weight
 */

fun main() = runBlocking {
    repeat(50_000) {
        launch {
            delay(5000L)
            print(".")
        }
    }
}