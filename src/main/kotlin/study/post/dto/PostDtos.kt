package study.post.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
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

    @field:NotBlank
    @JsonProperty("writer")
    var _writer : String = "익명(${id})",

    private val createDate: LocalDateTime = LocalDateTime.now()
) {
    val title: String
        get() = _title!!.toString()
    val content: String
        get() = _content!!.toString()
    val writer: String
        get() = _writer

    fun toEntity(): Post =
        Post(null, title, content, writer, createDate)
}

data class PostDtoResponse (
    val id: Long,
    val title: String,
    val content: String,
    val writer: String,
    val createDate: LocalDateTime
)