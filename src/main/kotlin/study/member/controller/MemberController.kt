//controller: Request를 받을 EndPoint
package study.member.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import study.common.dto.BaseResponse
import study.member.dto.MemberDtoRequest
import study.member.service.MemberService

//EndPoint: POST /api/member/signup
@RequestMapping("/api/member")
@RestController
class MemberController (
    private val memberService: MemberService
){
    /**
     * 회원가입
     */
    @PostMapping("/signup")
    //@Valid 추가: validation 체크
    //Unit: void
    fun signUp(@RequestBody @Valid memberDtoRequest: MemberDtoRequest): BaseResponse<Unit> {
        val resultMsg: String = memberService.signUp(memberDtoRequest)
        return BaseResponse(message = resultMsg)
    }
}