package reactive

import org.reactivestreams.Subscription
import reactor.core.publisher.BaseSubscriber
import reactor.core.publisher.Flux

fun main(args: Array<String>) {
    Flux.range(1, 10)
        .doOnRequest { println("request of $it") }
        .subscribe(
            object : BaseSubscriber<Int>() {
                // 구독 시점에서 요청 개수를 정의한다.
                override fun hookOnSubscribe(subscription: Subscription) {
                    request(1)
                }

                // Publisher에게 데이터를 받을 때에 대한 핸들링 로직, 데이터를 받으면 취소한다.
                override fun hookOnNext(value: Int) {
                    println("Cancelling after having received $value")
                    cancel()
                }
            }
        )
}