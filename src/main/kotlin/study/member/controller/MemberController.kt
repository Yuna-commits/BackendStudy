//controller: Request를 받을 EndPoint
package study.member.controller

import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import study.common.authority.TokenInfo
import study.common.dto.BaseResponse
import study.common.dto.CustomUser
import study.member.dto.LoginDto
import study.member.dto.MemberDtoRequest
import study.member.dto.MemberDtoResponse
import study.member.dto.MemberInfoDto
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

    /**
     * 로그인
     */
    @PostMapping("/login")
    fun login(@RequestBody @Valid loginDto: LoginDto): BaseResponse<TokenInfo> {
        val tokenInfo = memberService.login(loginDto)
        return BaseResponse(data = tokenInfo)
    }

    /**
     * 내 정보 조회
     */
    @GetMapping("/info")
    fun searchMyInfo(): BaseResponse<MemberDtoResponse> {
        val userId = (SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUser)
            .userId
        val response = memberService.searchMyInfo(userId)
        return BaseResponse(data = response)
    }

    /**
     * 내 정보 수정 -> 403 에러
     */
    @PutMapping("/info")
    fun changeMyInfo(@RequestBody @Valid memberInfoDto: MemberInfoDto):
            BaseResponse<Unit> {
        val userId = (SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUser)
            .userId
        memberInfoDto.userId = userId
        val resultMsg: String = memberService.changeMyInfo(memberInfoDto)
        return BaseResponse(message = resultMsg)
    }

    /**
     * 같은 기숙사 조회
     */
    @GetMapping("/dorm/info")
    fun getDormInfo(): BaseResponse<List<MemberDtoResponse>> {
        val userId = (SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUser)
            .userId
        val result = memberService.getDormInfo(userId)
        return BaseResponse(data = result)
    }
}