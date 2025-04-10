package study.post.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
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
    val writer : String,
    @Column(nullable = false, updatable = false)
    val createDate: LocalDateTime
)