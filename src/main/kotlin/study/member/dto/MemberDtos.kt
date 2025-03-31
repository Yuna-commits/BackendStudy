package study.member.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import study.common.annotation.ValidEnum
import study.common.status.Dormitory
import java.time.LocalDate

//회원가입시 입력받을 정보
data class MemberDtoRequest (
    val id: Long?,

    @field:NotBlank
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @field:Pattern(
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
) {
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
    val name: String
        get() = _name!!
    val email: String
        get() = _email!!
    val dormitory: Dormitory
        get() = Dormitory.valueOf(_dormitory!!)
}