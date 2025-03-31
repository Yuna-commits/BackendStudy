package study.common.status

//기숙사 타입
enum class Dormitory(val desc: String) {
    GOA( "고운A"),
    GOB("고운B"),
    GOC("고운C"),
    KYUNG11("경상11"),
    KYUNG12("경상12"),
    KYUNG13("경상13"),
    KYUNG14("경상14"),
}

enum class ResultCode(val msg: String) {
    SUCCESS("정상 처리 되었습니다."),
    ERROR("에러가 발생했습니다.")
}