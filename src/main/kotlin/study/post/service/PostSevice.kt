package study.post.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import study.post.dto.PostDtoRequest
import study.post.repository.PostRepository

@Transactional
@Service
class PostService(
    private val postRepository: PostRepository,
) {
    /**
     * 게시글 작성
     */
    fun posting(postDtoRequest: PostDtoRequest): String {
        val post = postDtoRequest.toEntity()
        postRepository.save(post)
        return "게시글 작성 완료"
    }
}