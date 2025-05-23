//repository: 회원 정보 관련 Repository
package study.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import study.common.status.Dormitory
import study.member.entity.Member
import study.member.entity.MemberRole

interface MemberRepository : JpaRepository<Member, Long> {//JpaRepository 상속
    //loginId로 찾기, ID 중복 검사를 위해 필요
    fun findByLoginId(loginId: String): Member?
    //같은 기숙사 타입 찾기
    fun findByDormType(dormType: Dormitory): MutableList<Member>
}

interface  MemberRoleRepository : JpaRepository<MemberRole, Long>