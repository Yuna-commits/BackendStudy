package study.post.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import study.member.entity.Member
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Post(
    @Id
    //자동 기본키 생성, DB에 위임
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 100)
    val title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,

    @CreatedDate
    @Column(updatable = false)
    var createdAt: LocalDateTime,

    //1명의 사용자는 여러 개의 게시글 작성이 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val user: Member
)