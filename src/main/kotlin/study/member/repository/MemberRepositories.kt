package study.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import study.member.entity.Member

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByLoginId(loginId: String): Member?
}