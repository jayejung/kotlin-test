package coroutinetest

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
 extract function refactoring from #01
 */
fun main() = runBlocking {
    launch {
        doWorld()
    }
    println("hello")
}

// this is your first suspending function
private suspend fun doWorld() {
    delay(1000L)
    println("world!!")
}
