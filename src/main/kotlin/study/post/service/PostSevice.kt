package study.post.service

import org.springframework.stereotype.Service
import study.member.entity.Member
import study.member.repository.MemberRepository
import study.post.dto.PostDtoRequest
import study.post.entity.Post
import study.post.repository.PostRepository

@Service
class PostService(
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository,
) {
    fun createPost(postDtoRequest: PostDtoRequest, loginId: String): String {
        val member: Member = memberRepository.findByLoginId(loginId)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다.")

//        val post = Post (
//            title = postDtoRequest.title,
//            content = postDtoRequest.content,
//            user = member
//        )

        //postRepository.save(post)

        return "게시글 작성이 완료되었습니다."
    }
}