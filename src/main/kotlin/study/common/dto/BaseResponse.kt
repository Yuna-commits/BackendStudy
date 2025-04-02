//dto: 어플리케이션 전반에 공통적으로 사용할 수 있는 DTO
package study.common.dto

import study.common.status.ResultCode

data class BaseResponse<T> (
    val resultCode: String = ResultCode.SUCCESS.name,//결과 코드
    val data: T? = null,//조회, 처리시 데이터를 담아서 반환해줄 data
    val message: String = ResultCode.SUCCESS.msg,//처리 메세지
)