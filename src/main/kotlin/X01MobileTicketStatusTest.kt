fun main(args: Array<String>) {
    var inner1 = Outter.Inner("aaaInner", 12);
    var inner2 = Outter.Inner("aaaInner", 12);

    println(inner1 == inner2)

    var nonExFun = Friend3(name = "김선달", tel = "010-345-6789", type = 5)
    fun Friend3.showYourName() {
        println("My name is ${this.name}")
    }

    var ExFun = Friend3(name = "김선달", tel = "010-345-6789", type = 5)
    nonExFun.showYourName()
    ExFun.showYourName()

    val appTkts: List<Tkt> = listOf(
        Tkt(no = "1", rev = 0, rmk = 1L, omk = 2L),
        Tkt(no = "2", rev = 0, rmk = 1L, omk = 2L),
//        Tkt(no = "3", rev = 0, rmk = 1L, omk = 2L),
//        Tkt(no = "4", rev = 0, rmk = 1L, omk = 2L),
        Tkt(no = "5", rev = 0, rmk = 1L, omk = 2L),
        Tkt(no = "8", rev = 0, rmk = 1L, omk = 2L),
    )
    val srvTkts: List<Tkt> = listOf(
        Tkt(no = "1", rev = 0, rmk = 1L, omk = 2L),
        Tkt(no = "10", rev = 2, rmk = 1L, omk = 2L),
        Tkt(no = "2", rev = 1, rmk = 1L, omk = 2L),
        Tkt(no = "3", rev = 1, rmk = 1L, omk = 2L),
        Tkt(no = "5", rev = 0, rmk = 1L, omk = 2L),
        Tkt(no = "9", rev = 2, rmk = 1L, omk = 2L),
    )

    var mySrvTkts: List<Tkt> = srvTkts.filter { i -> i.omk == 2L }
    //.filter { i -> i.omk == 2L }
    mySrvTkts.toMutableList()

    mySrvTkts += Tkt(no = "5", rev = 0, rmk = 1L, omk = 2L)

    println("mySrvTkts: ${mySrvTkts}")

    for (item in mySrvTkts) {
        println(item)
        println(appTkts.contains(item))
    }

    println("appTkts.containsAll(srvTkts): ${srvTkts.containsAll(appTkts)}")

    val foundTkt: Tkt = srvTkts.foldRight(srvTkts.first(), { next, obj ->
        println("obj.no: ${obj.no}")
        if (obj.no == "3") {
            return@foldRight obj
        } else {
            next
        }
    })

    println("foundTkt: $foundTkt")

    getCompareForTkt(appTkts.sortedBy { it.no }, srvTkts.sortedBy { it.no })

    val cntRevZero: Int = srvTkts.filter { o -> (o.rev == 0 || o.rev == 1) }.size

    println("cntRevZero: ${cntRevZero}")

    val aaa: String = StatusEnum.AAA.name
    println(aaa)
}

data class Outter(
    val outerName: String,
    val outerAge: Int,
    val inners: List<Outter.Inner>,
) {
    data class Inner(
        val innerName: String,
        val innerAge: Int,
    )
}

open class TktAncestor(
)

data class Tkt(
    val no: String,
    val rev: Int,
    val rmk: Long,
    val omk: Long,
) : TktAncestor() {
    fun isSameTiNo(other: Tkt?): Boolean {
        return this.no == other?.no
    }

    fun isMoreThanOthers(other: Tkt?): Boolean {
        return this.no > other?.no ?: "zzzzzzzz"
    }
}

data class Tkt2(
    val no: String,
    val rev: Int,
    val rmk: Long,
    val omk: Long,
) : TktAncestor()

enum class StatusEnum(val desc: String) {
    AAA("aaa"),
    BBB("bbb")
}

private fun getCompareForTkt(appTkts: List<Tkt>, srvTkts: List<Tkt>) {
    println(appTkts)
    println(srvTkts)

    var (idxApps: Int, idxSrvs: Int) = 0 to 0

    while (srvTkts.getOrNull(idxSrvs) != null) {
        println("#########################################")
        println("srvTkts[idxSrvs]: ${srvTkts[idxSrvs]}")
        println("appTkts[idxApps]: ${appTkts.getOrNull(idxApps)}")

        if (srvTkts[idxSrvs].isSameTiNo(appTkts.getOrNull(idxApps))) {
            if (srvTkts[idxSrvs] == appTkts.getOrNull(idxApps)) {
                println("same TiNo!  no need to findAndModify")
            } else {
                println("same TiNo but differ! so need to findAndModify")
            }
            idxApps++; idxSrvs++

        } else {
            if (srvTkts[idxSrvs].isMoreThanOthers(appTkts.getOrNull(idxApps))) {
                println("do nothing")
                idxApps++
            } else {
                println("new sync: find and modify")
                // check issued, reissued, transissued, transreissued, checkedin, cscheckedin
                idxSrvs++
            }
        }
    }
}