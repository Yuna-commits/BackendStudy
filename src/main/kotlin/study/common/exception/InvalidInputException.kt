//exception: 예외 처리
package study.common.exception

//DB 확인 후 발생하는 예외 처리
class InvalidInputException (
    val fieldName: String = "",
    message: String = "Invalid Input"
) : RuntimeException(message)//RuntimeException 상속