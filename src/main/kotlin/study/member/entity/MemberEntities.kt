//entitiy: 회원 정보 관련 Entity
package study.member.entity

import jakarta.persistence.*
import study.common.status.Dormitory

@Entity
@Table(
    //loginId 중복X
    uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["loginId"])]
)
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, length = 30, updatable = false)
    //updatable = false -> 업데이트시 loginId는 제외, 변경X
    val loginId: String,

    @Column(nullable = false, length = 100)
    val password: String,

    @Column(nullable = false, length = 10)
    val name: String,

    @Column(nullable = false, length = 30)
    val email: String,

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)//db에 Dormitory의 이름(STRING)을 그대로 입력
    val dormitory: Dormitory,
)