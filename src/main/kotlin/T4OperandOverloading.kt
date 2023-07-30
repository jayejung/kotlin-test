fun main(args: Array<String>) {
    val m1 = Score(100, 200)
    val m2 = Score(300, 400)
    println("m1 + m2: ${m1 + m2}")
    println("m1 * m2: ${m1 * m2}")
    println("m1 unaryMinus: ${-m1}")

    val p1 = Customer("홍길동", "010-1234-5678")
    val p2 = Customer("김선달", "010-5678-1234")
    println("p1 < p2 : ${p1 < p2}")
    println("p1 > p2 : ${p1 > p2}")

    val instance1 = InvokeOperator("Learning")
    instance1("Kotlin")

    val aa: Int = 123123
    var bb: Int = 123123

    println(aa != bb)
}

/*
    data class는 equals(), hashCode(), toString(), copy() 와 같은
    함수를 자동으로 생성해준다.
 */
data class Score(val a: Int, val b: Int) {
    operator fun plus(other: Score): Score {
        return Score(a + other.a, b + other.b)
    }
}

/*
    클래스 외부에서 extension 함수로 할 수도 있다. 확장함수를 사용하면 클래스를 변경하지 않고 새로운 기능(함수)
    를 추가할 수 있다.
    아래는 연산자 overloading 실제 객체 * 객체는 객체.times(객체)임.
 */
operator fun Score.times(other: Score): Score {
    return Score(a * other.a, b * other.b)
}

operator fun Score.unaryMinus(): Score {
    return Score(-a, -b)
}

class Customer(val name: String, val phone: String) : Comparable<Customer> {
    override fun compareTo(other: Customer): Int {
        return compareValuesBy(this, other, Customer::name, Customer::phone)
    }
}

class InvokeOperator(val makeMessage1: String) {
    operator fun invoke(makeMessage2: String) {
        println("$makeMessage1 $makeMessage2!")
    }
}