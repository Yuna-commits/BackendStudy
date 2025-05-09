package study.post.controller

import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import study.common.dto.BaseResponse
import study.common.dto.CustomUser
import study.post.dto.PostDtoRequest
import study.post.entity.Post
import study.post.service.PostService

@RequestMapping("/api/post")
@RestController
class PostController(
    private val postService: PostService
) {
    /**
     * 게시글 작성
     */
    @PostMapping("/posting")
    fun posting(@RequestBody @Valid postDtoRequest: PostDtoRequest): BaseResponse<String> {
        val userId = (SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUser)//CustomUser 형식으로 userId 받음
            .userId

        val result = postService.posting(postDtoRequest, userId)
        return BaseResponse(result)
    }

    /**
     * 전체 게시글 조회
     */
    @GetMapping("/")
    fun getAllPosts() : BaseResponse<MutableList<Post>> {
        val list = postService.getAllPosts()
        return BaseResponse(data = list)
    }

    /**
     * 특정 게시글 조회
     */
    @GetMapping("/{postId}")
    //@PathVariable: 클라이언트 측에서 url 에 인자를 전달하는 경우에 사용, url 경로에 변수를 넣어줌
    fun getPost(@PathVariable postId: Long) : BaseResponse<Post> {
        val result = postService.getPost(postId)
        return BaseResponse(data = result)
    }
}