package study.post.controller

import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import study.common.dto.BaseResponse
import study.common.dto.CustomUser
import study.post.dto.PostDtoRequest
import study.post.dto.PostDtoResponse
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
            .principal as CustomUser)
            .userId

        val result = postService.posting(userId, postDtoRequest)
        return BaseResponse(result)
    }

    /**
     * 게시글 작성자 조회
     */
    @GetMapping("/userInfo")
    fun searchUser(): BaseResponse<PostDtoResponse> {
        val userId = (SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUser)
            .userId
        val response = postService.searchUser(userId)
        return BaseResponse(data = response)
    }

    /**
     * 게시글 작성자 수정
     */
    @PutMapping("/userInfo")
    fun changeUserName(@RequestBody @Valid postDtoRequest: PostDtoRequest):
            BaseResponse<Unit> {
        val userName = (SecurityContextHolder
            .getContext())
        postDtoRequest._writer = userName.toString()
        val resultMsg: String = postService.changeUserName(postDtoRequest)
        return BaseResponse(message = resultMsg)
    }
}