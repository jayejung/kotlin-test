package reactive

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

// Mono, Flux, Subscribe 개요
fun main(args: Array<String>) {


    println("\n\n\nMono sample")
    val stringMono = Mono.just("beer1").log()
    stringMono.subscribe()

    val intMono = Mono.just(123).log()
    intMono.subscribe()

    println("\n\n\nFlux sample")
    println("\n\n\none Int flux with subscriber")
    val oneFluxInt = Flux.just(1).log()
    oneFluxInt.subscribe()

    println("\n\n\nfour Int flux with subscriber")
    val manyFluxInt = Flux.just(1, 2, 3, 4).log()
    manyFluxInt.subscribe()

    println("\n\n\nfour String flux from List with subscriber")
    val listToFlux = Flux.fromIterable(listOf("one", "two", "three", "four")).log()
    listToFlux.subscribe()

    println("\n\n\nfour String flux from Set with subscriber")
    val setToFlux = Flux.fromIterable(setOf(1, 2, 3, 4)).log()
    setToFlux.subscribe()

    // subscribe #1
    println("\n\n\n5 Int flux made by range with subscriber")
    val intFlux = Flux.range(1, 5).log()
    intFlux.subscribe()

    // subscribe #2
    println("\n\n\n10 Int flux made by range with subscriber's param: subs' Consumer")
    val intFlux2 = Flux.range(5, 10)
    intFlux2.subscribe { num ->
        println("item: $num")
    }

    // subscribe #3
    println("\n\n\n10 Int flux made by range with subscribe's params: subs' Consumer and ErrorConsumer")
    class NumberException : RuntimeException("숫자가 왜 15이지?")

    val intFlux3 = Flux.range(10, 10)
        .handle<Int> { num, sink ->
            if (num == 15)
                sink.error(NumberException())
            else
                sink.next(num)
        }

    intFlux3.subscribe(
        { num ->
            println("Item: $num")
        },
        { error ->
            println("Error Message: ${error.message}")
        }
    )

    // subscribe #4
    println("\n\n\n10 Int flux made by range with subscriber's params: subs' Consumer, ErrorConsumer and complete Consumer")
    val intFlux4 = Flux.range(11, 10)
        .handle<Int> { num, sink ->
            if (num == 3) sink.error(NumberException())
            else sink.next(num)
        }

    intFlux4.subscribe(
        { num ->
            println("Item: $num")
        },
        { error ->
            println("Error message: ${error.message}")
        },
        {
            println("subscription is completed")
        }
    )

    // subscribe #5
    println("\n\n\n50 Int flux made by range with subscriber's params: Consumer, ErrorConsumer, complete Consumer and subs' Consumer")
    val intFlux5 = Flux.range(10, 50).log()
        .handle<Int> { num, sink ->
            if (num == 1000) sink.error(NumberException())
            else sink.next(num)
        }

    intFlux5.subscribe(
        { num ->
            println("Item: $num")
        },
        { error ->
            println("Error message: ${error.message}")
        },
        {
            println("subscription is completed")
        },
        { sub ->
            sub.request(5)
        }
    )

    println("\n\n\n50 Int flux made by range with subscriber's params: Consumer, ErrorConsumer, complete Consumer and subs' Consumer")
    println("+ dispose() method which stops subscription")
    val intFlux6 = Flux.range(10, 50).log()
        .handle<Int> { num, sink ->
            if (num == 1000) sink.error(NumberException())
            else sink.next(num)
        }

    // subscribe #5 with dispose method
    intFlux6.subscribe(
        { num ->
            println("Item: $num")
        },
        { error ->
            println("Error message: ${error.message}")
        },
        {
            println("subscription is completed")
        },
        { sub ->
            sub.request(8)
        }
    ).dispose()
}