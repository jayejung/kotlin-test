package coroutinetest

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
 An explicit job
 */
fun main() = runBlocking {
    val job = launch { // launch a new coroutine and keep a reference to its job
        delay(1000L)
        println("World!")
    }
    println("Hello")
    job.join()  // wait until child coroutine completes
    println("Done")
}