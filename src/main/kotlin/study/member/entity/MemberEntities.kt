//entitiy: 회원 정보 관련 Entity
package study.member.entity

import jakarta.persistence.*
import study.common.status.Dormitory
import study.common.status.ROLE
import study.member.dto.MemberDtoResponse

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
) {//1 : N 연결
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    val memberRole: List<MemberRole>? = null

    //DTO 변경 함수
    fun toDto(): MemberDtoResponse =
        MemberDtoResponse(
            id!!,
            loginId,
            name,
            email,
            dormitory.desc //dormitory 에 해당하는 값을 받음
        )
}

@Entity
class MemberRole(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    val role: ROLE,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_user_role_member_id"))
    val member: Member,
)