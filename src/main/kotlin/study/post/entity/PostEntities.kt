package study.post.entity

import jakarta.persistence.*
import study.post.dto.PostDtoResponse
import java.time.LocalDateTime

@Entity
@Table(
    //loginId 중복X
    uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["loginId"])]
)
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    //title, content 수정 가능, writer, date 는 수정 불가능
    @Column(nullable = false, length = 30, updatable = true)
    val title: String,

    @Column(nullable = false, length = 500, updatable = true)
    val content: String,

    @Column(nullable = false, length = 10, updatable = false)
    var writer : String,

    @Column(nullable = false, updatable = false)
    val createDate: LocalDateTime
) {
    //DTO 변경 함수
    fun toDto(): PostDtoResponse =
        PostDtoResponse(
            id!!,
            title,
            content,
            writer,
            createDate
        )
}