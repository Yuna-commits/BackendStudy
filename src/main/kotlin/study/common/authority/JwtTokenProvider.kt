package study.common.authority

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

const val EXPIRATION_MILLISECONDS: Long = 1000 * 60 * 30//60초 * 30개 -> 30분
//JwtTokenProvider: 토큰 생성, 정보 추출, 검증
@Component
class JwtTokenProvider {
    @Value("\${jwt.secret}")
    lateinit var secretKey: String

    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))}

    /**
     * Token 생성
     */
    fun createToken(authentication: Authentication): TokenInfo {
        val authorities: String = authentication
            .authorities
            .joinToString(",", transform = GrantedAuthority::getAuthority)

        val now = Date()
        val accessExpiration = Date(now.time + EXPIRATION_MILLISECONDS)

        //Access Token 생성
        val accessToken = Jwts
            .builder()
            .subject(authentication.name)
            .claim("auth", authorities)//auth 라는 이름으로 권한을 담음
            .issuedAt(now)//토큰 발행 시간
            .expiration(accessExpiration)//유효 시간
            .signWith(key, Jwts.SIG.HS256)//사용한 알고리즘
            .compact()

        return TokenInfo("Bearer", accessToken)
    }

    /**
     * Token 정보 추출
     */
    fun getAuthentication(token: String): Authentication {//parameter: access token
        val claims: Claims = getClaims(token)

        //auth 가 없으면 RuntimeException
        val auth = claims["auth"] ?: throw RuntimeException("잘못된 토큰입니다.")

        //권한 정보 추출
        val authorities: Collection<GrantedAuthority> = (auth as String)
            .split(",")
            .map {SimpleGrantedAuthority(it)}

        val principal: UserDetails = User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    /**
     * Token 검증
     */
    fun validateToken(token: String): Boolean {
        try {//문제가 없으면 true 반환
            getClaims(token)
            return true
        } catch (e: Exception) {//exception 별 처리
            when (e) {
                is SecurityException -> {} //Invalid JWT Token
                is MalformedJwtException -> {} //Invalid JWT Token
                is ExpiredJwtException -> {} //Expired JWT Token
                is UnsupportedJwtException -> {} //Unsupported JWT Token
                is IllegalArgumentException -> {} //JWT claims string is empty
                else -> {}
            }
            println(e.message)
        }
        return false
    }

    private fun getClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
}