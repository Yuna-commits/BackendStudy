//JwtAuthenticationFilter: 토큰 정보 검사, Security Context Holder 에 정보 기록
package study.common.authority

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean

class JwtAuthenticationFilter (
    private val jwtTokenProvider: JwtTokenProvider
) : GenericFilterBean() {//GenericFilterBean 상속
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val token = resolveToken(request as HttpServletRequest)//access token 정보

        //정상 토큰이면 정보 추출 -> SecurityContextHolder 에 기록
        if (token != null && jwtTokenProvider.validateToken(token)) {
            val authentication = jwtTokenProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }

        chain?.doFilter(request, response)
    }

    //request 로부터 Header Authorization 문자를 가져와 bearer 와 맞는지 찾음
    //bearer 와 맞으면 key 값만 추출
    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")

        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }
}