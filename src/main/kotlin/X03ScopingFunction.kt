fun main(args: Array<String>) {

    //########## let ###########//
    // fun <T, R> T.let(block: (T) -> R): R

    println("########## let ###########")
    val person: Person = Person("", 0)
    person.name = "james"
    person.age = 56
    println("Person: ${person}")

    // 위의 예시에서는 set을 위해서 name, age를 각각 호출 호출해줬는데,이러한 블럭을 묶어서 간결한 코드로 정리 가능.
    val person2 = Person("", 0)
    val resultIt = person2.let {
        it.name = "james2"
        it.age = 57
        it  /* (T) -> R 부분에서의 R에 해당하는 변환값 */
    }

    val resultStr = person.let {
        it.name = "steve"
        it.age = 29
        "${it.name} is ${it.age}"
    }

    val resultUnit = person.let {
        it.name = "Joe"
        it.age = 63
        // (T) -> R 부분에서 R에 해당하는 변환값 없음
    }

    println("resultIt: ${resultIt}")
    println("resultStr: ${resultStr}")
    println("resultUnit: ${resultUnit}")
    // 블럭의 마지막 return값에 따라 let의 return 값 형태도 달라진다.

    // 또한 T?.let {} 형태에서의 let 블럭안에는 non-null만 들어올 수 있어 non-null 체크 시에 유용하게 사용 가능하다.
    // 객체를 선언하는 상황일 경우에는 elvis operator(?:)를 사용해서 기본값을 지정해 줄 수도 있다.
    val nameStr = person?.let { it.name } ?: "Default name"

    //########## with ###########//
    // fun <T, R> with(receiver: T, block: T.() -> R): R
    println("########## with ###########")

    // 아래 예제에서 with(T) 타입으로 Person을 받으면 {} 내의 블럭 안에서 곧바로 name이나 age프로퍼티에 접근할 수 있다.
    // with는 non-null의 객체를 사용하고 블럭의 return값이 필요하지 않을 때 사용한다.
    // 그래서 주로 객체의 함수를 여러 개 호출할 때 그룹화하는 용도로 활용된다.
    with(person) {
        println(name)
        println(age)
        // 자기자신을 반환해야 하는 경우 it가 아닌 this를 사용함.
    }

    //########## run #1  ###########//
    // fun <T, R> T.run(block: T.() -> R): R
    println("########## run #1 ###########")
    val ageNextYear = person.run {
        ++age
    }

    println("ageNextYear: ${ageNextYear}")

    //########## run #2  ###########//
    // fun <R> run(block: () -> R): R
    val person3 = run {
        val name = "James3"
        val age = 58
        Person(name, age)
    }

    println("person3: ${person3}")

    //########## apply ###########//
    // fun <T> T.apply(block: T.() -> Unit): T
    val resultApply = person.apply {
        name = "James4"
        age = 60
    }

    println("resultApply: ${resultApply}")
    println("person: ${person}")

    //########## also ###########//
    // fun <T> T.also(block: (T) -> Unit): T
    val result = person.also {
        it.name = "James5"
        it.age = 61
    }

    println("result: ${result}")

    // 블럭 함수의 입력으로 T를 받았기 때문에 it를 사용해 프로퍼티에 접근하는 것을 볼 수 있음.
    // 그래서 객체의 속성을 전혀 사용하지 않거나 변경하지 않고 사용하는 경우에 also를 사용함.

    // 혹은 객체의 데이터 유효성을 확인하거나, 디버그, 로깅등의 부가적인 목적으로 사용할 때에 적합하다.
    val numbers = arrayListOf("one", "two", "three")
    numbers.also { println("add 하기전에 print: $it") }
        .add("four")
    println("add한 후에 print: $numbers")
}

data class Person(
    var name: String,
    var age: Int,
)
