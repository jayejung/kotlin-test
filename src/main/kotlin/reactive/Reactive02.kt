package reactive

import org.reactivestreams.Subscription
import reactor.core.publisher.BaseSubscriber
import reactor.core.publisher.Flux
import reactor.core.publisher.SignalType


// custom BaseSubscriber
fun main(args: Array<String>) {
    val sampleSubscriber = SampleSubscriber<Int>(4)
    val intFlux = Flux.range(1, 10)
    intFlux.subscribe(sampleSubscriber)
}

class SampleSubscriber<T>(
    private val exitValue: T
) : BaseSubscriber<T>() {
    override fun hookOnSubscribe(subscription: Subscription) {
        println("Subscribed")
        request(1)
    }

    override fun hookOnNext(value: T) {
        //super.hookOnNext(value)
        println(value)

        if (value != exitValue)
            request(1)
    }

    override fun hookOnComplete() {
        println("Completed")
        //super.hookOnComplete()
    }

    override fun hookOnError(throwable: Throwable) {
        println("Error")
        //super.hookOnError(throwable)
    }

    override fun hookOnCancel() {
        println("Cancel")
        //super.hookOnCancel()
    }

    override fun hookFinally(type: SignalType) {
        println("Finally")
        //super.hookFinally(type)
    }
}