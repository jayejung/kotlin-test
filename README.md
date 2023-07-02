# kotlin-test

### 연산자와 연산자 오버로딩 (변환 코드)

#### 연산자 별 변환되는 코드

##### 산술 연산자

> a + b : a.plus(b)   
> a - b : a.minus(b)   
> a * b : a.time(b)   
> a / b : a.div(b)   
> a % b : a.rem(b), a.mod(b)

##### 단항 연산자

> +a : a.unaryPlus()   
> -a : a.unaryMinus()   
> !a : a.not()   
> ++a, a++ : inc   
> --a, a-- : dec

##### 복합 연산자

> a += b : a.plusAssign(b)   
> a -= b : a.minusAssign(b)   
> a *= b : a.timesAssign(b)   
> a /= b : a.divAssign(b)   
> a %= b : a.modAssign(b)

##### 비트 연산자

> shl: signed shift left   
> shr: signed shift right   
> ushr: unsigned shift right   
> and: bitwise and   
> or: bitwise or   
> xor: exclusive or   
> inv: bitwise inversion

##### 동등 비교 연산자

> a == b : a?.equals(b) ?: (b == null)
> a === b : 오버로딩 안됨   
> a != b : !(a?.equals(b) ?: (b == null))   
> a !== b : 오버로딩 안됨   
> not : 좌우의 피연산자에 대해 논리 not 연산 수행

> a?.equals(b) ?: (b == null)
>* a가 null이 아닌지 확인하고, 아니면 a의 equals(b)를 호출. 그리고, equals(b)의 결과가 null이 아니라면 그 값을 반환하고, null 이면 (b == null)의 결과 값을 반환.   
   > ?: 연산자를 엘비스 연산자라고 함

##### 그 외의 비교 연산자

> a > b : a.compareTo(b) > 0   
> a < b : a.compareTo(b) < 0   
> a >= b : a.compareTo(b) >= 0   
> a <= b : a.compareTo(b) <= 0

##### in 연산자

> a in b : b.contains(a)   
> a !in b : !b.contains(a)

##### 범위(..)연산자

> a..b : a.rangeTo(b)

##### 인덱스 연산자

* []가 get() 혹은 set()으로 변환됨

> a[i] : a.get(i)   
> a[i, j] : a.get(i, j)   
> a[i_1, ...., i_n] : a.get(i_1, ...., i_n)    
> a[i] = b : a.set(i, b)   
> a[i, j] = b : a.set(i, j, b)   
> a[i_1, ...., i_n] = b : a.set(i_1, ...., i_n, b)

##### invoke 연산자

* 함수와 마찬가지로 인스턴스도 소괄호()를 붙혀서 인자를 전달 할 수 있고, 해당 인스턴스는 operand키워드를 지정하여 연산자 오버로딩한 invoke()함수를 정의하면 됨

> a() : a.invoke()   
> a(i) : a.invoke(i)
> a(i, j) : a.invoke(i, j)
> a(i_1, ...., i_n) : a.invoke(i_1, ...., i_n)

##### 타입 연산자: is, !is

* java의 instanceof

### null 처리 mechanism

##### ?.

> eg: a?.length   
> a가 null이 아니면 length 속성을 반환, a가 null이라면 null 반환

##### ?:

> eg: a?.length ?: 0   
> a가 null이 아니면 length 속성을 반환, a가 null이라면 ?:(앨비스 연산자) 뒤의 표현식을 반환

##### !!!

> eg: a!!.length   
> a가 null이 아니면, 정상적인 코드 수행하고, null이면 NPE 발생

##### as, as?

> val s1: String? = s2 as String?
>* s2가 String 타입에 적합한 것일 때 s2의 값을 String 타입으로 변환하여 s1에 할당
>* 만약, s2의 값이 String에 부적합하다면 ClassCastingException이 발생함
   > val s1: String? = s2 as? String?
>* 위와 같이 하면, s2의 값이 String 타입에 부적합할 때 예외를 발생하키지 않고 null 값을 반환

### 코드 실행 제어

##### if, when

 ```kotlin
   val result = if (param == 1) {
    "one"
} else if (param == 2) {
    "two"
} else {
    "three"
}
 ```

* 다른 언어와 마찬가지로 if - else if - else 사용하였음
* 중요한건, 코틀린에서는 if문을 하나의 표현식으로 간주하므로 이 코드처럼 변수의 대입문에 if문을 사용할 수 있음
* when은 java의 switch - case와 비슷해 보이지만, 더 간결하고 훨씬 많은 기능을 제공함.

 ```kotlin
   // -------------> #1
when (inputType) {
    1 -> println("type - 1")
    2, 3 -> println(
        "type - 2, 3)  
        else
    -> {
        pritnln("undefinded")
    }
}

// -------------> #2
when (inputType) {
    checkInputType(inputType) -> {
        print("normal type")
    }
    else -> print("abnormal type")
}

// -------------> #3
var start = 0
var end = 50
when (score) {
    in start..(end / 4) -> println("excellent") // ..은 start <= score <= (end / 4)
    50 -> print("average")
    in start..end -> println("in reasonable range")
    else -> print("out of range")
}

// -------------> #4
val city = "San Francisco"
val isSanfrancisco = when (city) {
    is String -> city.startsWith("San Francisco")
    else -> false
}

// -------------> #5
when {
    city.length == 0 -> println("enter name of city")
    city.substring(0, 13).equals("San Francisco") -> println("This is San Francisco")
    else -> println(city)
}

fun checkInputType(inputType: Int): Int {
    if (inputType >= 1 && inputType < 3) {
        return inputType
    }
    return -1
}
 ```

* 자바의 switch case와 유사하나, break;가 필요 없음.
* 1번 예시는 값 -> 실행 블록으로 구성되어 있음. 값은 쉼표를 통해서 여러 테스트를 할 수 있음.
  모두 일치 하지 않으면 else 구문을 실행함.
* 2번 예시는 when에 전달된 값과 checkInputType function의 실행 결과를 비교함.
* 3번 예시애서는 값의 range를 처리하기 위해서는 in {시작값}..{끝값} 으로 설정이 가능함.
* 4번 예시에서는 is String으로 값의 타입을 확인하고, String이면 시작문자열을 비교하고 Boolean을 리턴함.
  값 isSanFrancisco는 코틀린에서 알아서 Boolean으로 타입 추론한다.
* 5번 예시는 첫번째 비교에서는 값 city의 length가 0인지 검사하고, 두번째 비교는 city의 substring 결과를 비교한다.
* when도 if와 동일하게 표현식으로 간주함. 즉 오른쪽에서 생성하는 값을 사용 가능함.

##### for

```kotlin
    // ------------> #1
val item1 = listOf("apple", "banana", "kiwi")
for (item in item1) {
    println(item)
}

// ------------> #2
val item2 = listOf("apple", "banana", "kiwi")
for (index in item2.indices) {
    println("item a $index is ${item2[index]}")
}

// ------------> #3
val item3 = arrayOf("apple", "banana", "kiwi")
for (index in item3.indices) {
    println("item at $index is ${item3[index]}")
}
```               

* 첫 번째 for에서는 listOf 함수로 List 객체를 생성할 수 있음. 이렇게 생성된 list는 읽기만 가능하고 저장된 값을 변경할 수는 없음(val 이니).
* 두 번째 for에서는 List 객체의 각각의 item을 index로 참조하였음. for문에는 item2.indices 변수를 참조함.
* 세 번째 for에서는 listOf 대신 arrayOf를 사용하여 List가 아닌 array를 생성하였음.
* for loop에서 우리가 카운터(loop 반복 회숫)를 지정하고 사용할 때는 아래의 방법으로 하면 됨.

```kotlin 
    for (i in 1..100) {
    ...
}         // 1부터 100까지 반복. i값은 1씩 증가
for (i in 1 until 100) {
    ...
}    // 1부터 100까지 반복하되 100은 제외. i는 1씩 증가
for (i in 2..10 step 2) {
    ...
}   // 2부터 10까지 반복하되 i는 2씩 증가
for (i in 10 downTo 1) {
    ...
}    // 10부터 시작하여 i 값을 1씩 감소하면서 반복
 ```

##### while과 do-while loop

* while과 do-while loop는 다른 언어와 동일하다.

### 함수

##### 함수 선언과 호출

* 함수 선언시 fun 키워드를 함수명 앞에 지정함.
* 함수는 코드 파일의 어디든 바로 정의 할 수 있음(또는 클래스 내부의 멤버로 정의도 가능).
* 매개변수는 변수이름:타입 의 형태로 지정함. 함수의 반환하는 값의 타입은 함수 정의 문장 끝에 콜론을 추가한 후 지정.

```kotlin
  fun min(valueLeft: Int, valueRight: Int): Int {
    return if (valueLeft < valueRight) valueLeft else valueRight
}
```

* 여기서 min은 함수명, (valueLeft: Int, valueRight: Int)는 매개변수이고, Int는 이 함수의 반환 타입.
* 만약 함수에서 값을 반환하지 않으면, Int 대신 Unit 타입으로 지정(자바의 void). 그러나, Unit 타입은 생략 가능.
* 코틀린에서 if 문은 표현식이므로 return 문에 직접 사용 가능함.
* 아래와 같이 더 축약된 형태로 할 수도 있음.

```kotlin
  fun main(valueLeft: Int, valueRIght: Int) = if (valueLeft > valueLeft) valueLeft else valueRight
```

* min 함수의 return type을 지정하지 않았으나, 표현식(if)이 assign되고 리턴타입이 Int이므로 min함수의 리턴타입을 Int로 타입 추론.
* 함수의 매개변수는 변수이름:타입 형태로 지정. 그리고, 지명인자(named argument)도 지원.  
  즉, 함수를 호출할 때 각 매개변수의 값외에 이름과 값을 같이 지정할 수 있음. 추가로 기본값도 정의 가능하고 기본값에는 표현식(수식이나 다른 함수 호출 등을 지정 가능).

```kotlin
  fun min(valueLeft: Int = 0, valueRight: Int = 0) =
    if (valueLeft < valueRight) valueLeft else valueRight
```

* 아래와 같은 6가지 형태로 호출이 가능.

```kotlin
    min(100, 50)
min(100)                                // valueRight 매개변수는 기본값 0이 됨
min()                                   // 두 변수 모두 기본값 사용
min(valueLeft = 50, valueRight = 100)   // 지명인자(named parameter)를 사용하여 값을 전달
min(valueRight = 100, valueLeft = 50)   // 지명인자(named parameter)를 사용하여 정의된 순서와 무관하게 값을 전달
min(valueLeft = 50)                     // named parameter와 default value를 같이 사용
```

##### 가변 인자

* 함수를 호출할 때 인자의 개수를 가변적으로 전달 할 수 있음. 이때 vararg 키워드를 사용(자바에서는 타입명..).  
  그리고 가변 인자들은 배열로 전달.

```kotlin
    fun <T> newList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) // ts는 가변인자이므로 배열
        result.add(t)
    return result
}
```

* newList함수는 가변 인자를 배열로 받아서 List로 반환한다. 그리고 제너릭을 사용하여 어떤 타입의 가변 인자도 처리 가능하게 작성되었음.

```kotlin
  var list = newList(1, 2, 3)
```

* 또한, newList를 호출할 때 각 요소와 함께 배열도 같이 전달할 수 있음. 이때는 배열명 앞에 *연산자를 붙여야함.

```kotlin
    val e = arrayOf(7, 8, 9)
val list = newList(1, 2, 3, *e, 5)
println(list)
```

* 전달된 *e는 배열의 요소를 하나씩 풀어서 전달하게 되고, *을 확산(spread) 연산자라고 함.
* 위에서 정의한 newList 함수는 코틀린 표준 라이브러리의 listOf() 함수와 동일한데, 역시 spread 연산자 전달이 가능함.

##### 함수의 종류

* 함수는 scope에 따라 몇가지로 구분됨
  1. 첫 번째, 클래스 외부에 함수를 정의. 즉 소스 코드 파일에 바로 정의(func main과 같이...). 이런것을 최상위 수준(top level) 함수라고 함.
     이 함수는 애플리케이션에서 공통으로 사용하는 기능을 처리하는데 필요함. 반면 자바의 경우는 모든 것이 클래스에 정의 되어야 하므로 이렇게 못하고,특정 클래스에
     static 메소드를 모아 두고 사용해야했음. 기본적으로 빌드되면서 public static final 메서드로 변환됨.
  2. 두 번째로, 함수는 클래스 내부에 정의하여 사용(멤버함수).
    ```kotlin
    class Customer() {
    fun checkId() {....}
    }
    Customer().checkId()
    ```  
  위의 예시에서 checkId는 멤버 함수. Customer 클래스의 인스턴스를 생성(Customer())하여 호출 할 수 있음.
  3. 세 번째로, 지역함수를 선언하여 사용할 수 있음. 다른 함수 내부에서 선언하여 사용.
    ```kotlin
        fun main(args: Array<String>) {
            pringln(calcCombination(45, 6)) // 로또 복권의 모든 조합 가능한 번호 개수를 출력
        }
        
        fun calcCombination(whole: Int, selected: Int): Double {
            if ((selected > whole) || (selected <= 0) || (whole <= 0)) {
                return -1.0
            } else if (selected == whole) {
                return 1.0
            }
  
            fun calcFactorial(num: Int): Double {
                var total: Double = 1.0
                for (i in num downTo 1) {
                    total *= 1
                }
                return total
            }
            return calcFactorial(whole) / 
                (calcFactorial(whole - selected) * calcFactorial(selected))     
        }
    ```
  지역 함수에서는 자신을 포함하는 외부 함수의 인자나 변수를 그냥 사용할 수 있음.
  4. 네 번째로, 제네릭(generic)함수를 선언하고 사용할 수 있음.
    ```kotlin
    fun <T> newList(vararg ts: t): List<T> {...}
    ```
  여기서 <T>가 제너릭 타입.
  5. 다섯 번째로, 확장(extension)함수를 선언하고 사용할 수 있음. 이것을 특정 클래스로부터 상속받지 않고 해당 클래스의 기능을 확장할때 사용.
     예를 들어, 코틀린의 컬렉션 중에는 MutableList가 있으며, 이것은 저장된 요소를 변경할 수 있는 List를 나타냄. 그리고 mutableListOf()함수를 사용하면 MutableList 객체를
     생성 할 수 있음.
     이때 저장된 요소를 바꿔치기 하는 기능의 swap() 함수를 추가할 필요가 있다면, MutableList로 부터 상속받는 서브 클래스를 정의하고 멤버 함수를 추가하면 되지만,
     확장 함수를 사용하면 그래도 하지 않아도 쉽게 기능을 추가할 수 있음.
    ```kotlin
    fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
        var tmp = this[index1]          // 여기서 this는 현재의 MutableList객체를 의미
        this[index1] = this[index2]
        this[index2] = tmp
    }
    ```
  이 코드에서 보듯이, 확장 함수를 정의할 때는 함수 이름 앞에 대상이 되는 클래스를 지정해야 함. 여기서는 어떤 타입의 요소든 MutableList에 저장 할 수 있도록 제네릭 타입을 <T>로 지정하였음.
  또한, 함수 내부에서 사용하는 this 키워드는 현재 사용 중인 MutableList객체를 참조함.  
  확장 함수가 정의 되었으므로, 이제 MutableList를 생성하고 다음과 같이 확장 함수인 swap()을 호출할 수 있음.
    ```kotlin
    val l = mutableListOf(1, 2, 3)
    l.swap(0, 2)
    ```
  위의 예시는 확장함수 swap을 통해서 첫 번째 요소와 세 번째 요소를 바꿔치기 하는 예시.
  6. 여섯 번째로, 중위(infix) 함수가 있음. 이 함수는 이항 연산자처럼 두 개의 피연산자 사이에 함수 호출을 넣음.
     이것은 새로운 부류의 함수라기 보다는 사용을 편리하게 하기 위해 코틀린에 추가된 기능임. 대표적인 사용 예가 비트 연산.
     비트 연산자의 한가지 사용 예는 아래와 같음.
  ```kotlin
  println(8 shr 2)
  ```
  shift right 연산임. 8비트 정수값인 8의 각 비트를 오른쪽으로 2비트 이도 ㅇ시켜서 출 결과는 2(8/2^2)임.
  이처럼 중위 함수를 사용하면 마치 연산자처럼 사용 할 수 있으므로 편리함. 중위 함수를 정의할 때는 fun 키워드 앞에 infix키워드를 추가.
  실제로 코틀린의 Int 클래스에는 다음과 같은 shl()이 중위 함수로 정의 되어 있음. (shift left)
  ```kotlin
  infix fun shl(bitCount: Int): Int {...}
  ```
  따라서, 정수 타입의 값에 대해 이 함수를 두 가지 방법으로 호출하여 사용할 수 있음.
  ```kotlin
  8 shl 2
  // 혹은
  8.shl(2)
  ```
  중위 함수를 정의하려면 세 가지 조건을 충족해야 함. 첫 번째로, 클래스의 멤버 함수이거나 확장 함수이어야 함.
  두 번째는, 매개변수가 한 개여야 함. 세 번째는, infix 키워드로 함수가 정의되어야함.
  7. 일곱 번째로, 재귀(tail recursive)함수가 있음. 일련의 코드를 반복해서 실행해야 하는 알고리즘의 경우는 루프를 사용하거나 재귀 함수를 사용할 수 있음.
     단, 재귀함수를 사용할 때는 스택 오버플로우의 위험을 따르고 시스템의 부담도 커짐. 따라서, 코틀린에서는 이런 단점을 줄이고 실행 속도도 빠르며 효율적인 루프를 사용하는 재귀함수를 제공함.
     이때는 다음의 예와 같이 fun앞에 tailrec 키워들 추가함.
  ```kotlin
  tailrec fun calcFactorial1(num: Int): Double {
      if (num == 1) {
          return 1.0
      } else { 
          return (num * calcFactorial1(num - 1)).toDouble())
      }
  }
  ```
  여기서 tailrec 키워드를 제거해도 마찬가지로 재귀 함수로 사용할 수 있지만 비효율적이므로 그렇게 하지 않는 것이 좋음.  
  반복 루프를 사용해서 이와 같은 동일한 기능을 수행하는 함수를 작성하면 다음과 같음.
  ```kotlin
  fun calcFactorial2(num: Int): Double {
      var total: Double = 1.0
      for (i in num downTo 1) {
          total *= i
      }
      return total
  }
  ```
  8. 마지막으로, 상위차(higher-order)함수를 선언하고 사용할 수 있음. 이것은 다른 함수나 람다식을 인자로 받아 실행시키거나 반환할 수 있는 함수임.
     또는 이름이 없는 익명(anonymous) 함수를 사용할 수 있으며, 성능을 개선하기 위해(inline) 함수를 사용할 수 있음(요건 나중에 람다식에서 설명).

### 클래스와 인터페이스

##### 클래스 선언과 생성자

* 아래의 코드를 작성해서 살펴보자

```kotlin
fun main(args: Array<String>) {
  var f1 = Friend1()
  f1.name = "박문수"
  f1.tel = "010-123-4567"
  f1.type = 5
  println(f1.name + ", " + f1.tel + ", " + f1.type)

  var f2 = Friend2()
  f2.name = "홍길동"
  f2.tel = "010-456-1234"
  f2.type = 5     // 이 속성에 지정한 setter인 set() 함수가 호출되어 실행된다.
  println(f2.name + ", " + f2.tel + ", " + f2.type)

  var f3 = Friend3(name = "김선달", tel = "010-345-6789", type = 5)
  println("${f3.name}, ${f3.tel}, ${f3.type}")

  var f4 = Friend4("전신주", "010-333-5555", 3)
  println("${f4.name}, ${f4.tel}, ${f4.type}")

}

class Friend1 {
  var name: String = ""
  var tel: String = ""
  var type: Int = 4           // 1: "학교", 2: "회사", 3: "SNS", 4: "기타"
}

class Friend2 {
  var name: String = ""
  var tel: String = ""
  var type: Int = 4           // 1: "학교", 2: "회사", 3: "SNS", 4: "기타" 
    set(value: Int) {
      if (value < 4) field = value   // 1: "학교", 2: "회사", 3: "SNS", 4: "기타"
      else field = 4
    }
}

class Friend3(var name: String, var tel: String, var type: Int) {
  init {
    type = if (type < 4) type else 4    // 1: "학교", 2: "회사", 3: "SNS", 4: "기타"
  }
}

class Friend4 {
  var name: String
  var tel: String
  var type: Int

  constructor(name: String, tel: String, type: Int) {
    this.name = name
    this.tel = tel
    this.type = if (type < 4) type else 4   // 1: "학교", 2: "회사", 3: "SNS", 4: "기타"
  }
}
```

* Friend1 클래스는 자바처럼 property를 정의. 그러나 생성자를 정의하지 않았으므로, 속성을 직접 초기화해 주어야 함.
  그리고, 생성자 정의를 하지 않았으므로, 기본 생성자가 자동으로 생성됨(자바와 동일). 속성도 변수 처럼 val(값 변경 안됨)이나 var(값을 변경 가능)로 지정할 수 있음.
* 코틀린은 내부적으로 속성(필드)와 getter/setter가 연계되어 동작하게 되어 있음. 즉 속성의 값을 읽거나 변경할 때 자동으로 getter/setter가 호출됨.
* getter/setter를 별도로 정의하지 않아도 되지만, 필요하다면 커스텀 getter/setter를 정의할 수 있음.
* 예를 들어 type속성은 친구를 나타내며, 1부터 4까지의 값만 지정되어야 함. 그러나 Friend1 클래스의 인스턴스를 생성하면 어떤 값이든 지정 가능함.
  따라서 type속성의 값이 1부터 4까지만 변경 가능하도록 하려면 이 속성의 커스텀 setter를 지정해야 함.
  그래서 Friend2 클래스에서는 type 속성의 커스텀 setter를 정의하였음. 이때 코틀린에서는 자바와 다르게 해당 속성에 set() 함수로 저정함.
  그리고, set()함수 내부에서 해당 속성을 엑세스하려면 field 키워드를 사용해야함. type 속성의 set()함수는 f2.type = 5처럼 속성을 액세스할때 자동 호출 됨.
* field 키워드에 좀 더 보자. 우리는 Friend2 클래스에 필드처럼 type이라는 이름의 속성을 정의하고 사용하면됨.
  그러나, 코틀린에서는 내부적으로 속성 이름 앞에 밑줄(_)을 추가한 _type이라는 private 필드를 생성하고 값을 보존함.
  이것을 후원필드(backing field)라고 하며, 멤버변수로 사용됨. 그러나, 직접 private 필드에 액세스할 수 없으므로 대신 field키워드를 사용하는 것임.
  이렇게 하는 이유는 코틀린 언어 자체에서 클래스의 갭슐화를 지원하기 위해서임.
* Friend3은 named paramter를 통해서 인스턴스화가 가능하고,
  ```kotlin
  val f3 = Friend3(name = "김선달", tel = "010-345-6789", type = 5)
  ```
  인자의 이름 없이 호출해도 된다.
  ```kotlin
  val f3 = Friend3("김선달", "010-345-6789", 5)
  ```
  생성자를 통해서 초기화하고, 별도 처리를 위해서는 init 블록을 이용하면 됨.
* 코틀린 클래스에서도 자바와 유사하게 추가로 보조 생성자를 가질 수 있음. 이때는 클래스 몸체 내부에 constructor 키워드로 정의해야함.
* Friend4 클래스에서는 기본 생성자는 정하지 않고 보조 생성자를 정의하였음.

  


