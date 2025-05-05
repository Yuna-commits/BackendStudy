package study.post.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import study.post.entity.Post
import java.time.LocalDateTime

data class PostDtoRequest(
    var id: Long? = null,

    @field:NotBlank //빈칸 허용 x
    @JsonProperty("title")
    private val _title : String?,

    @field:NotBlank
    @JsonProperty("content")
    private val _content : String?,

    //게시글 좋아요 수
    private val likes : Long = 0,
    private val createDate: LocalDateTime = LocalDateTime.now()
) {
    val title: String
        get() = _title!!.toString()
    val content: String
        get() = _content!!.toString()

    //Member 의 name 을 writer 로 사용
    fun toEntity(writer: String): Post {
        return Post(null, title, content, writer, likes, createDate)
    }
}