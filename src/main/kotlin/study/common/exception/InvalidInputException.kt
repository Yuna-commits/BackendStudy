package study.common.exception

//예외처리
class InvalidInputException (
    val fieldName: String = "",
    message: String = "Invalid Input"
) : RuntimeException(message)