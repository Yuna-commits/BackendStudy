package study.post.controller

import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import study.common.dto.BaseResponse
import study.common.dto.CustomUser
import study.post.dto.PostDtoRequest
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

}