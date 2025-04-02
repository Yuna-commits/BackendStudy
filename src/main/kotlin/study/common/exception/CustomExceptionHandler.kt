//exception: 예외 처리 Handler
package study.common.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import study.common.dto.BaseResponse
import study.common.status.ResultCode

@RestControllerAdvice
class CustomExceptionHandler {

    //@Valid(DTO의 validation)에서 발생한 exception
    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun methodArgumentNotValidException(ex: MethodArgumentNotValidException) : ResponseEntity<BaseResponse<Map<String, String>>> {
        val errors = mutableMapOf<String, String>()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage
            errors[fieldName] = errorMessage ?: "Not Exception Message"
        }

        return ResponseEntity(BaseResponse(ResultCode.ERROR.name, errors, ResultCode.ERROR.msg), HttpStatus.BAD_REQUEST)
    }

    //사용자 생성 exception
    @ExceptionHandler(InvalidInputException::class)
    protected fun invalidInputException(ex: InvalidInputException) : ResponseEntity<BaseResponse<Map<String, String>>> {
        val errors = mapOf(ex.fieldName to (ex.message ?: "Not Exception Message"))
        return ResponseEntity(BaseResponse(ResultCode.ERROR.name, errors, ResultCode.ERROR.msg), HttpStatus.BAD_REQUEST)
    }

    //그 외의 처리하지 못 한 모든 exception
    @ExceptionHandler(Exception::class)
    protected fun defaultException(ex: InvalidInputException) : ResponseEntity<BaseResponse<Map<String, String>>> {
        val errors = mapOf("미처리 에러" to (ex.message ?: "Not Exception Message"))
        return ResponseEntity(BaseResponse(ResultCode.ERROR.name, errors, ResultCode.ERROR.msg), HttpStatus.BAD_REQUEST)
    }
}