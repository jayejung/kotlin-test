import java.time.LocalDateTime

fun main(args: Array<String>) {

    fun UserDTO.mapDomain() = User(
        id = this.id ?: -1,
        name = this.name.orEmpty(),
        age = (
                if (this.isDomestic == true) {
                    this.age + 1
                } else {
                    this.age
                }),
        validPeriod = User.ValidPeriod(this.birthDate, this.birthDate.plusYears(1))
    )

    // do something and get UserDto from somewhere
    val orgD: LocalDateTime = LocalDateTime.parse("2021-01-01T13:30:00")
    val newD: LocalDateTime = orgD.plusDays(1).withHour(22).withMinute(0)

    LocalDateTime.now()

    val orgT: String = "1300"
    val hour: Long = orgT.substring(0, 2).toLong()
    val min: Int = orgT.substring(2, 4).toInt()
    println("hour: ${hour}")
    println("min: ${min}")

    println("orgD: ${orgD}")
    println("newD: $newD")

    val userDto: UserDTO = UserDTO(1, "jaye", 30, true, orgD)

    val user: User = userDto.mapDomain()

    println(user.toString())
}


data class UserDTO(
    val id: Int? = null,
    val name: String? = null,
    val age: Int,
    val isDomestic: Boolean? = true,
    val birthDate: LocalDateTime,
)

data class User(
    val id: Int,
    val name: String,
    val age: Int,
    val validPeriod: ValidPeriod?,
) {
    data class ValidPeriod(
        val startDate: LocalDateTime,
        val endDate: LocalDateTime,
    )
}

