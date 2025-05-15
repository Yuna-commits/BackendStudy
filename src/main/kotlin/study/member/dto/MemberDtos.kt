//dto: 회원 정보 관련 DTO
package study.member.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder
import study.common.annotation.ValidEnum
import study.common.status.Dormitory
import study.member.entity.Member
import study.member.repository.MemberRepository

//회원가입시 입력받을 정보
data class MemberDtoRequest (
    var userId: Long?,

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
    @JsonProperty("dormType")
    private val _dormType: String?,
) {//Custom Getter
    //암호화 기능 추가
    private val encoder = SCryptPasswordEncoder(16,8,1,8,8)

    val loginId: String
        get() = _loginId!!
    private val password: String
        get() = encoder.encode(_password)
    val name: String
        get() = _name!!
    val email: String
        get() = _email!!
    val dormType: Dormitory//String?을 enum class 로 변환
        get() = Dormitory.valueOf(_dormType!!)

    //Entity 반환
    fun toEntity(): Member =
        Member(userId, loginId, password, name, email, dormType)
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

data class MemberDtoResponse (
    val userId: Long,
    val loginId: String,
    val name: String,
    val email: String,
    val dormType: String,
)

//내 정보 수정시 입력받을 정보
//password, name, email, dormType 수정 가능 -> 수정하고 싶은 정보만 수정
//입력된 정보는 Member에 저장됨, MemberInfo는 수정할 값을 임시 보관하는 용도
//4가지 중 수정하려는 것만 선택적으로 Member에 전달
//-> Custom Getter 필요 없음
data class MemberInfoDto(
    var userId: Long,
    val password: String? = null,
    val name: String? = null,
    val email: String? = null,
    val dormType: String? = null
) {
    //MemberInfoDto -> MemberDtoRequest 변환
    //변환된 MemberDtoRequest는 changeMyInfo(memberDtoRequest: MemberDtoRequest)에 사용됨
    //기존 changeMyInfo 로직 유지
    fun applyTo(member: Member): MemberDtoRequest {
        return MemberDtoRequest(
            userId = this.userId,
            _loginId = member.loginId,
            //password를 변경하지 않았으면 this.password == null -> member.password 대입
            _password = this.password ?: member.password,
            _name = this.name ?: member.name,
            _email = this.email ?: member.email,
            _dormType = this.dormType ?: member.dormType.name
        )
    }
}