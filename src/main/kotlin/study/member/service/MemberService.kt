//비즈니스 로직
package study.member.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder
import org.springframework.stereotype.Service
import study.common.authority.JwtTokenProvider
import study.common.authority.TokenInfo
import study.common.exception.InvalidInputException
import study.common.status.Dormitory
import study.common.status.ROLE
import study.member.dto.LoginDto
import study.member.dto.MemberDtoRequest
import study.member.dto.MemberDtoResponse
import study.member.entity.Member
import study.member.entity.MemberRole
import study.member.repository.MemberRepository
import study.member.repository.MemberRoleRepository

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    /**
     * 회원가입
     */
    //MemberDtoRequest class: 회원가입시 입력받을 정보
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        //ID 중복 검사 -> ID 조회가 가능하면 member != null
        var member: Member? = memberRepository.findByLoginId(memberDtoRequest.loginId)
        if (member != null) {
            throw InvalidInputException("loginId", "이미 등록된 ID 입니다.")
        }

        //사용자 정보 저장
        member = memberDtoRequest.toEntity()
        memberRepository.save(member) //insert

        //권한 저장
        val memberRole = MemberRole(null, ROLE.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다."
    }

    /**
     * 로그인 -> 토큰 발행
     */
    fun login(loginDto: LoginDto): TokenInfo {
        val member = memberRepository.findByLoginId(loginDto.loginId)
            ?: throw InvalidInputException("로그인 아이디 혹은 비밀번호가 틀렸습니다.")
        val encoder = SCryptPasswordEncoder(16,8,1,8,8)
        if(!encoder.matches(loginDto.password, member.password)) {
            throw InvalidInputException("로그인 아이디 혹은 비밀번호가 틀렸습니다.")
        }

        //loginDto 의 password 암호화 -> DB에 존재하는 내용으로 토큰 발행
        val authenticationToken =
            UsernamePasswordAuthenticationToken(loginDto.loginId, member.password)
        val authentication =
            authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        //DB에 있는 유저네임과 비교, 문제가 없으면 사용자에게 토큰 발행

        return jwtTokenProvider.createToken(authentication)
    }

    /**
     * 내 정보 조회
     */
    fun searchMyInfo(userId: Long): MemberDtoResponse {
        //해당하는 id가 없으면 예외 처리
        val member = memberRepository.findByIdOrNull(userId)
            ?: throw InvalidInputException("userId", "회원번호(${userId}): 존재하지 않는 사용자입니다.")
        return member.toDto()
    }

    /**
     * 내 정보 수정
     */
    fun changeMyInfo(memberDtoRequest: MemberDtoRequest): String {
        val member = memberDtoRequest.toEntity()
        memberRepository.save(member)
        return "정보 수정이 완료되었습니다."
    }

    /**
     * 같은 기숙사 조회 -> userId로 기준 기숙사 타입 결정
     * !!으로 null 이 아닌 경우에만 기숙사 타입 받음
     */
    fun getDormInfo(userId: Long): List<MemberDtoResponse> {
        val dormType = memberRepository.findByIdOrNull(userId)!!.dormType
        val result = memberRepository.findByDormType(dormType)
        return result.map { it.toDto() }
    }
}