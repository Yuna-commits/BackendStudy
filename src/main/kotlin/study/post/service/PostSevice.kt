package study.post.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import study.common.exception.InvalidInputException
import study.member.repository.MemberRepository
import study.post.dto.PostDtoRequest
import study.post.entity.Post
import study.post.repository.PostRepository

@Transactional
@Service
class PostService(
    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository,
) {
    /**
     * 게시글 작성 -> 회원가입한 사용자만 작성 가능
     */
    fun posting(postDtoRequest: PostDtoRequest, userId: Long): String {
        //관련 없는 토큰이 들어왔을 때, 토큰이 안 들어오면 에러
        val member = memberRepository.findByIdOrNull(userId)
            ?: throw InvalidInputException("id", "회원번호(${userId})가 존재하지 않는 사용자입니다.")

        //게시글 작성자 writer == member.name
        val post = postDtoRequest.toEntity(member.name)
        postRepository.save(post)
        return "게시글 작성이 완료되었습니다."
    }

    /**
     * 전체 게시글 조회
     */
    fun getAllPosts(): MutableList<Post> {
        return postRepository.findAll()
    }

    /**
     * 특정 게시글 조회
     */
    fun getPost(postId: Long): Post {
        val post = postRepository.findPostById(postId)
            ?: throw InvalidInputException("${postId}번 게시글은 존재하지 않습니다.")
        return post
    }
}