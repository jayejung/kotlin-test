package reactive

import org.reactivestreams.Subscription
import org.slf4j.LoggerFactory
import reactor.core.publisher.BaseSubscriber
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import kotlin.random.Random

fun main(args: Array<String>) {
    /*
        backpressure 커스터마이징 #1
     */
    println("\n\n\nBaseSubscriber상속하는 subscriber를 구현하여, hookOnSubscribe을 오버라이드")
    Flux.range(1, 10)
        .doOnRequest { println("request of $it") }
        .subscribe(
            object : BaseSubscriber<Int>() {
                // 구독 시점에서 요청 개수를 정의한다.
                override fun hookOnSubscribe(subscription: Subscription) {
                    request(1)
                }

                // Publisher에게 데이터를 받을 때에 대한 핸들링 로직, 데이터를 받으면 취소한다.
                // Flux가 10개를 publishing할 수 있지만, 1개를 받고 subscription을 취소하였음.
                override fun hookOnNext(value: Int) {
                    println("Cancelling after having received $value")
                    cancel()
                }
            }
        )

    println("\n\n\nFlux.generate 메소드로 flux를 생성하는 것외에는 위의 코드와 동일함 ")
    val randomInfiniteFlux = Flux.generate<Int> { sink ->
        sink.next(Random.nextInt(1, 11))
    }.doOnRequest { println("request of $it") }

    randomInfiniteFlux.subscribe(
        object : BaseSubscriber<Int>() {
            override fun hookOnSubscribe(subscription: Subscription) {
                request(1)
            }

            override fun hookOnNext(value: Int) {
                println("data is $value, if data is 10 then cancel")
                if (value == 10) {
                    println("cancel!")
                    cancel()
                } else {
                    println("request")
                    request(1)
                }
            }
        }
    )

    println("\n\n\nBuffer 사용")
    val upstream = Flux.range(1, 40)

    upstream.buffer(10)
        .subscribe(
            object : BaseSubscriber<List<Int>>() {
                override fun hookOnSubscribe(subscription: Subscription) {
                    println("requset 2")
                    request(2)
                }

                override fun hookOnNext(value: List<Int>) {
                    println("value: $value")
                    request(2)
                }

                override fun hookOnComplete() {
                    println("Completed!")
                }
            }
        )

    println("\n\n\nPrefetch 사용 - 미적용")
    // prefetch 미적용
    var i = 1
    val upstream1 = Flux.generate<Int> {
        Thread.sleep(1000L)
        it.next(i++)
    }.log()

    upstream1.subscribe(
        object : BaseSubscriber<Int>() {
            val logger = LoggerFactory.getLogger(javaClass)
            var time: Long = 0

            override fun hookOnSubscribe(subscription: Subscription) {
                time = System.currentTimeMillis()
                request(10)
            }

            override fun hookOnNext(value: Int) {
                val fetchTime = System.currentTimeMillis() - time
                logger.info("value: $value, fetchTime: $fetchTime")
                time = System.currentTimeMillis()

                Thread.sleep(500L)
            }
        }
    )

    println("\n\n\nPrefetch 사용 - 적용")
    // prefetch 적용
    var i1 = 1
    val upstream2 = Flux.generate<Int> {
        Thread.sleep(1000L)
        it.next(i1++)
    }.log()

    val downstream2 = upstream2.publishOn(Schedulers.boundedElastic(), 4).log()

    downstream2.subscribe(
        object : BaseSubscriber<Int>() {
            val logger = LoggerFactory.getLogger(javaClass)
            var time: Long = 0

            override fun hookOnSubscribe(subscription: Subscription) {
                time = System.currentTimeMillis()
                request(10)
            }

            override fun hookOnNext(value: Int) {
                val fetchTime = System.currentTimeMillis() - time
                logger.info("value: $value, fetchTIme: $fetchTime")
                time = System.currentTimeMillis()

                Thread.sleep(500L)
            }
        }
    )

    Thread.sleep(100000)
}