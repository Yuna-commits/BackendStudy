package study.post.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import study.post.entity.Post
import java.time.LocalDate
import java.time.LocalDateTime

data class PostDtoRequest(
    val id: Long? = null,

    @field:NotBlank //빈칸 허용 x
    @JsonProperty("title")
    private val _title : String?,

    @field:NotBlank
    @JsonProperty("content")
    private val _content : String?,

    private val writer : String = "익명의 사용자",
    private val createDate: LocalDateTime = LocalDateTime.now()
) {
    val title: String
        get() = _title!!.toString()
    val content: String
        get() = _content!!.toString()

    fun toEntity(): Post =
        Post(null, title, writer, content, createDate)
}