package study.post.service

import jakarta.transaction.Transactional
import org.antlr.v4.runtime.Token
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import study.common.authority.JwtTokenProvider
import study.common.authority.TokenInfo
import study.common.exception.InvalidInputException
import study.member.dto.LoginDto
import study.member.repository.MemberRepository
import study.post.dto.PostDtoRequest
import study.post.dto.PostDtoResponse
import study.post.repository.PostRepository

@Transactional
@Service
class PostService(
    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    /**
     * 게시글 작성 -> 회원가입한 사용자만 작성 가능
     */
    fun posting(id: Long, postDtoRequest: PostDtoRequest): String {
        memberRepository.findByIdOrNull(id)
            ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않는 사용자입니다.")
        val post = postDtoRequest.toEntity()
        postRepository.save(post)
        return "게시글 작성이 완료되었습니다."
    }

    /**
     * 게시글 작성자 조회
     */
    fun searchUser(id: Long): PostDtoResponse {
        val member = postRepository.findByIdOrNull(id)
            ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않는 사용자입니다.")
        return member.toDto()
    }

    /**
     * 작성자 이름을 토큰으로 찾은 사용자 이름으로 수정
     */
    fun changeUserName(postDtoRequest: PostDtoRequest): String {
        val writer = postDtoRequest.toEntity()
        postRepository.save(writer)
        return "작성자 이름 수정이 완료되었습니다."
    }
}