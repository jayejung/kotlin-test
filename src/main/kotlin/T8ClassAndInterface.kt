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