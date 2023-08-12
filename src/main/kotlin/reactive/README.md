# reactive

## Mono, Flux, Subscribe 간략한 내용 (Reactive01.kt)

### Dependency

```groovy
plugins {
  id("io.spring.dependency-management") version "1.1.2"
  ....
  ....
}

dependencies {
  implementation("io.projectreactor:reactor-core:3.5.8")
  ....
  ....
}
```

### Mono, Flux

* Reactor에서는 Publisher를 구현함과 동시에 풍부한 연산자도 제공해주는 조합 가능한 reactive type을 Mono, Flux를 통헤서 제공.
* Mono는 0 또는 1([0,1])개의 항목을 가지는 reactive sequence.
* Flux는 여러개([0,N]) 항목을 가지는 reactive sequence.

#### Mono

* Mono는 [0,1]개의 항목에 대한 비동기 시퀀스를 나타내는 Publisher임.
* Publisher는 Subscriber에게 데이터를 전달할때는 onNext 시그널을, 데이터를 모두 전달했다고 알릴 때는 onComplete 시그널을,
  에러를 전달할때는 onError시그널을 트리거함.  
  Subscriber에서는 각각 onNext(), onComplete(), onError() 메서드로 전달함.  
  즉, Subscriber에서는 이 세 가지에 대한 핸들링 로직을 구현하면 됨.

```kotlin
    val monoString = Mono.just("test")
val monoInt = Mono.just(123)

// 데이터가 없는 객체는 아래와 같이
val monoEmpty = Mono.empty<Int>()
// 제너릭에 타입을 명시해야함. 비어있는 Mono여서 타입 추론을 할 수 없으므로...

// 3가지 시그널을 방출하지 않는 Mono도 생성 가능
val monoNever = Mono.never<Int>()
```

#### Flux

* Flux는 [0,N]개의 항목에 대한 비동기 시퀀스를 나타내는 Publisher임.
* Flux는 Mono에 비해 여러가지 factory method를 제공함.

```kotlin
    // just 메서드를 통해서 Flux 생성.
val oneFluxInt = Flux.just(1)
val manyFluxInt = Flux.just(1, 2, 3, 4)

// Flux.fromIterable() 메서드를 통해서 Iterable객체를 Flux로 변환.
val listToFlux = Flux.fromIterable(listOf(1, 2, 3, 4))
val setToFlux = Flux.fromIterable(setOf(1, 2, 3, 4))
// 그리고, Flux.range(start, count) 메서드를 통해 Int형 Flux를 생성할 수 있음.
val rangeFlux = Flux.range(5, 10) // 5, 6, 7 ..., 14
```

#### Subscribe

* Flux(Mono) 객체를 생성만 하면 아무 일도 일어나지 않음. 실제로 Flux(Mono)를 구독을 해야 데이터를 읽어올 수 있음.
  Flux(Mono)를 구독하는 subscribe 메서드는 여러가지가 있는데 하나하나 살펴 보자

```kotlin
    subscribe() // (1): 시퀀스를 구독하고 트리거한다.

subscribe(Consumer < ? super T > consumer) // (2): 방출된 값 각각으로 어떤 행동을 한다.

subscribe(
  Consumer < ? super T > consumer,
  Consumer < ? super Throwable > errorConsumer
) // (3): (2) + 에러가 발생할 때는 별도의 행동을 한다.

subscribe(
  Consumer < ? super T > consumer,
  Consumer < ? super Throwable > errorConsumer,
  Runnable completeConsumer
) // (4): (3) + 시퀀스가 완료되었을 때는 또 다른 행동을 한다.

subscribe(
  Consumer < ? super T > consumer,
  Consumer < ? super Throwable > errorConsumer,
  Runnable completeConsumer,
  Consumer < ? super Subscription > subscriptionConsumer
)
// (5): (4) +  몇 개의 데이터를 구독할 것인지 (취소할 것인지) 정의
```

##### subscribe()

* subscribe()는 시퀀스를 구독하고 트리거하여 Flux의 값을 받아옴.

```kotlin
    val intFlux = Flux.range(1, 5).log()
intFlux.subscribe()
// Flux.log() 메소드는 Publisher가 보내는 신호를 로그로 남기는 operator임.
```

* 위의 코드를 실행하면 아래와 같은 결과가 나옴.

```kotlin
    [INFO](main) | onSubscribe([Synchronous Fuseable] FluxRange . RangeSubscription)
[INFO](main) | request(unbounded)
[INFO](main) | onNext(1)
[INFO](main) | onNext(2)
[INFO](main) | onNext(3)
[INFO](main) | onNext(4)
[INFO](main) | onNext(5)
[INFO](main) | onComplete()
```

* Publisher와 Subscriber의 데이터 전달 과정은 보통 다음과 같은 순서로 이뤄짐.
  * subscribe() 하는 순간 onSubscribe() 호출. 이때 Publisher와 연동된 subscription을 받음.  
    전달 받은 subscription을 이용하여 Publisher에게 데이터를 요청.
  * 그 다음, downstream에서 request(unbounded)를 호출하여 upstream에게 무제한 요청이 전파됨.
  * Publisher가 onNext 시그널을 보내 데이터를 하나씩 전달함. subscriber에게 데이터가 전달될 경우 onNext(data) 메소드가 호출됨.
  * Publisher가 모든 데이터를 다 전달한 경우 subscriber에게 onComplete 시그널을 보냄. 이때 subscriber의 onComplete() 메서드가 호출됨.
* 위의 경우에는 onSubscribe(), request(), onNext(), onComplete(), onError() 메서드를 구현하지 않고 기본 메서드를 사용하고 있어서 onComplete()를 호출하고는
  아무일도 발생하지 않음.

##### subscribe(Consumer<? super T> consumer)

* subscribe(Consumer<? super T> consumer)에서 방출한 값 각각에 대한 핸들링 로직을 정의할 수 있음.
  예제에서는 방출된 값 하나하나를 print함. 여기서 정의하는 로직이 onNext()에 대한 핸들링 로직임.  
  `Params: consumer - the consumer to invoke on each value (onNext signal)`

```kotlin
    val intFlux = Flux.range(1, 5)
intFlux.subscribe { num ->
  println("item: $num")
}
```

##### subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer)

* Flux에서 에러 시그널을 보냈을 경우 별도의 어떤 행동을 정의할 수 있음. Flux에서 error를 방출하는 순간 subscribe()은 종료됨.

```kotlin
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
```

    * handle()은 publisher에 대한 Operator 로직을 정의함. publisher에서 방출되는 값은 handle() operator를 거쳐 데이터가 변형됨. handle()에 대한 내용은 추후에 다시 확인.

* 실행 결과

```kotlin
    Item: 10
Item: 11
Item: 12
Item: 13
Item: 14
Error Message : 숫자가 왜 15 이지 ?
```

#### subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer, Runnable completeConsumer)

* 이번 subscribe는 완료 신호에 대한 행동도 정의할 수 있음.

```kotlin
    class NumberException : RuntimeException("숫자가 왜 10이지?")

val intFlux = Flux.range(1, 5)
  .handle<Int> { num, sink ->
    if (num == 10) sink.error(NumberException())
    else sink.next(num)
  }

intFlux.subscribe(
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
```

* 실행 결과:

```kotlin
Item: 1
Item: 2
Item: 3
Item: 4
Item: 5
subscription is completed
```

* 만약에 onError 시그널이 발생하면 onComplete 시그널은 발생하지 않음.

#### subscribe(Consumer<? super T> consumer,

        Consumer<? super Throwable> errorConsumer,
        Runnable completeConsumer,
        Consumer<? super Subscription> subscriptionConsumer)

* 마지막 subscribe에서는 몇 개의 데이터를 구독할 것인지 또는 구독 취소를 할 것인지 정할 수 있음.

```kotlin
    class NumberException : RuntimeException("숫자가 왜 1000이지?")

val intFlux = Flux.range(10, 50).log()
  .handle<Int> { num, sink ->
    if (num == 1000) sink.error(NumberException())
    else sink.next(num)
  }

intFlux.subscribe(
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
    // sub.cancel()
  }
)
```

* 데이터가 50개의 Flux를 구독할때 처음 5개만 요청하도록 subscribe()메소드를 정의 하였음.
  결과는 5개 데이터만 방출함. 여기서는 5개 데이터를 받고 완료신호를 받지 않아서 구독이 종료되지는 않음.
* 실행결과

```kotlin
[INFO](main) | onSubscribe([Synchronous Fuseable] FluxRange . RangeSubscriptionConditional)
[INFO](main) | request(5)
[INFO](main) | onNext(10)
Item: 10
[INFO](main) | onNext(11)
Item: 11
[INFO](main) | onNext(12)
Item: 12
[INFO](main) | onNext(13)
Item: 13
[INFO](main) | onNext(14)
Item: 14
```

* 5개의 데이터를 받고 구독을 종료시킬 수 있음. subscribe()의 리턴값은 disposable 인터페이스이기 때문에
  subscribe()메소드 뒤에 dispose() 메서드를 호출하면 됨.

```kotlin
    class NumberException : RuntimeException("숫자가 왜 1000이지?")

val intFlux = Flux.range(10, 50).log()
  .handle<Int> { num, sink ->
    if (num == 1000) sink.error(NumberException())
    else sink.next(num)
  }

intFlux.subscribe(
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
    // sub.cancel()
  }
).dispose()
```

* 실행결과

```kotlin
[INFO](main) | onSubscribe([Synchronous Fuseable] FluxRange . RangeSubscriptionConditional)
[INFO](main) | request(5)
[INFO](main) | onNext(10)
Item: 10
[INFO](main) | onNext(11)
Item: 11
[INFO](main) | onNext(12)
Item: 12
[INFO](main) | onNext(13)
Item: 13
[INFO](main) | onNext(14)
Item: 14
[INFO](main) | cancel()
```

#### BaseSubscriber (Reactive02.kt)

* 람다식으로 subscriber를 구성하는 방법 외에 더 일반적인 기능을 사용할 수 있도록 Subscribe를 구성 할 수 있음.
  Reactor에서는 이러한 Subscriber를 구성하기 위해서 BaseSubscriber 클래스를 상속하는 클래스를 구현하면 됨.
* 주의할 점은 BaseSubscriber인스턴스는 다른 Publisher를 구독한 경우에는 이미 구독 중인 Publisher의 구독을 취소하기 때문에 재활용이 불가능함.
  인스턴스에 여러 Publisher를 구독한다면 구독자의 onNext 메서드가 병렬로 호출되어야 하는데 이는 onNext메서드가 병렬로 호출되지 않아야 한다는 Reactive Stream의 규칙을 위반하기 때문임.
  그래서 잘 사용하지 않는 방식임.

```kotlin
    class SampleSubscriber<T>(
  private val exitValue: T
) : BaseSubscriber<T>() {

  override fun hookOnSubscribe(subscription: Subscription) {
    println("Subscribed")
    request(1)
  }
  override fun hookOnNext(value: T) {
    println(value)

    if (value != exitValue)
      request(1)
  }
  override fun hookOnError(throwable: Throwable) {}
  override fun hookOnCancel() {}
  override fun hookOnComplete() {}
  override fun hookFinally(type: SignalType) {}
}
```

* BaseSubscriber를 커스텀 할 때 최소한 hookOnSubscribe()메서드와 hookOnNext메서드는 오버라이딩 해서 구현해야함.
* hookOnSubscribe()메서드는 처음 구독후 몇개의 데이터를 요청할 것인지 request 이벤트를 보내는 메서드임. hookOnNext()메서드는 onNext이벤트를 받아 데이터를 처리하는 기능을 함.
  여기서는 데이터 처리 로직을 구현하면 됨. 추가로, request메서드로 몇개의 데이터를 더 요청하기 위해 request메서드를 사용할 수도 있음.
  예시에서는 exitValue를 받으면 더 이상 request를 하지 않도록 구현되어 있음.
* 아래는 실제로 SampleSubscribe로 구독하는 코드임. 4를 받으면 출력후에 더 이상 request를 받지 않음.

```kotlin
    val sampleSubscriber = SampleSubscriber<Int>(4)
val intFlux = Flux.range(1, 10)

intFlux.subscribe(sampleSubscriber)

```

* 실행 결과:

```kotlin
Subscribed
1
2
3
4
```

## Backpressure (Reactive03.kt)

* Subscriber가 Publisher를 구독하게 되면, Publisher가 데이터를 push하여 Subscriber에게 전달함.
  이때, Publisher는 Subscriber에게 onNext 시그널을 보내 Subscriber에게 데이터를 전달한다는 것을 알림.
  또는 Publisher는 Subscriber에게 onComplete 시그널을 보내 완료가 되었음을 알리거나 onError 시그널을 보내 에러가 발생했음을 알림.
  그런데, Reactor에서는 반대로 Subscriber가 Publisher에게 어떠한 신호를 보내게 할 수 있는데 이를 Backpressure라고함.

![backpressure](https://github.com/jayejung/kotlin-test/blob/master/src/main/resources/images/backpressure.png)

### 기본 Backpressure: Unbounded request

* 첫 번째 요청은 구독 시점에서 최종 Subscriber로부터 오게됨. 요청을 하는 간단한 방법은 구독 시점에서 unbounded request,
  즉 request(Long.MAX_VALUE)를 트리거하는 방법임. Reactor에서는 다음과 같은 방식으로 unbounded request를 요청할 수 있음.
  * subscribe() 호출 (Consumer<? super Subscription> 람다식을 따로 정의하지 않으면 unbounded request)
  * block(), blockFirst(), blockLast() 호출
  * toIterable() (iterable 객체로 변형) 또는 toStream() (Reactive stream을 일반적인 Stream으로 변형)

### Backpressure 커스터마이징

* 그렇다면 unbounded request를 보내지 않고 요청을 커스터마이징 하는 방법이 있음.
  가장 간단한 방법은 BaseSubscriber를 상속하는 Subscriber를 직접 구현하여 hookOnSubscribe 메서드를 오버라이드 하면 됨.


