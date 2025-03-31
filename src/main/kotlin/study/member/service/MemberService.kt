package study.member.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import study.member.dto.MemberDtoRequest
import study.member.entity.Member
import study.member.repository.MemberRepository

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    /**
     * 회원가입
     */
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        //ID 중복 검사
        var member: Member? = memberRepository.findByLoginId(memberDtoRequest.loginId)
        if (member != null) {
            return "이미 등록된 ID 입니다."
        }

        member = Member( //생성자
            null,
            memberDtoRequest.loginId,
            memberDtoRequest.password,
            memberDtoRequest.name,
            memberDtoRequest.email,
            memberDtoRequest.dormitory
        )

        memberRepository.save(member) //insert

        return "회원가입이 완료되었습니다."
    }
}