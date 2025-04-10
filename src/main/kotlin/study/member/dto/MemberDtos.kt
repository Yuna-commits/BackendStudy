//dto: 회원 정보 관련 DTO
package study.member.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import study.common.annotation.ValidEnum
import study.common.status.Dormitory
import study.member.entity.Member

//회원가입시 입력받을 정보
data class MemberDtoRequest (
    val id: Long?,

    @field:NotBlank//빈 값을 받지 않음
    @JsonProperty("loginId")//loginId와 _loginId 연결, loginId 사용
    private val _loginId: String?,

    @field:NotBlank
    @field:Pattern(
        //정규 표현식: 특정한 규칙의 문자열 집합을 표현
        //-> 불특정 문자열이 특정 조건에 만족하는지 판별할 때 사용
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$",
        message = "영문, 숫자, 특수문자를 포함한 8~20자리로 입력해주세요"
    )
    @JsonProperty("password")
    private val _password: String?,

    @field:NotBlank
    @JsonProperty("name")
    private val _name: String?,

    @field:NotBlank
    @field:Email
    @JsonProperty("email")
    private val _email: String?,

    @field:NotBlank
    @field:ValidEnum(enumClass = Dormitory::class, message = "올바른 기숙사 타입을 선택해주세요.")
    @JsonProperty("dormitory")
    private val _dormitory: String?,
) {//Custom Getter
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
    val name: String
        get() = _name!!
    val email: String
        get() = _email!!
    val dormitory: Dormitory//String?을 enum class 로 변환
        get() = Dormitory.valueOf(_dormitory!!)

    //Entity 반환
    fun toEntity(): Member =
        Member(id, loginId, password, name, email, dormitory)
}

data class LoginDto (
    @field:NotBlank//빈 값을 받지 않음, 필수값
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @JsonProperty("password")
    private val _password: String?,
) {//custom getter
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
}