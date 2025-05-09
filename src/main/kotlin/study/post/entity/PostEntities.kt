package study.post.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null,

    //title, content, like 수정 가능, writer, date 는 수정 불가능
    @Column(nullable = false, length = 30, updatable = true)
    var title: String,

    @Column(nullable = false, length = 500, updatable = true)
    var content: String,

    @Column(nullable = false, length = 10, updatable = false)
    val writer : String,

    @Column(nullable = false, updatable = true)
    var likes : Long,

    @Column(nullable = false, updatable = false)
    val createDate: LocalDateTime
)