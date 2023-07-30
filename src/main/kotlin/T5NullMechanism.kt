fun main(args: Array<String>) {
    val s1: String? = null
    //val s2: String = null /* null을 non-null type String에 value가 안됨 */
    //val s3: String = s1   /* type missmatch required String, found String? */

    var a: String? = "learning kotlin"
    //println("a.length: ${a.length}")    /* a는 null 값이 될 수 있으므로, length 속성을 사용하기에 안전하지 않으므로, compile 에러
    if (a != null) println("a.length: ${a.length}") /* null check를 하면됨 */

    println(a?.length)  /* ?은 null 이 아니라면, a의 속성 length를 참조하라는 의미임. a가 null 이어도 NPE 발생하지 않고 그냥 null 반환함  */
    println(a?.substring(5)?.length)

    a = null
    println("a?.length: ${a?.length}")

    // ?가 null값여부를 확인하고 null이 아니면 다음 표현식을 수행하지만, null인 경우에는 null를 반환
    // ?: 는 null 처리를 하는 연산자 임
    a = null
    var b = a?.length ?: 0
    println("b : $b")

    // !! 연산자는 참조 변수의 값이 null이 아니면 정상적으로 코드를 수행하고 null이면 런타임시에 NPE 예외를 발생시킴.
    // kotlin의 null 처리 메카니즘과 반대 되는 연산자?
    // var c = a!!.length   /* 명시적인 null 상태는 체크함 */

    // null 처리 연산자 as, as?

    // IndexOutOfBoundsException을 피하고 null을 return 받을 수 있음
    val listStr: List<String> = listOf("111", "222", "333")
    for (i in 0..3) {
        println("listStr[i]: ${listStr.getOrNull(i)}")
    }

    var nullableStr: String? = null
    var notNullStr: String = nullableStr.orEmpty()
}