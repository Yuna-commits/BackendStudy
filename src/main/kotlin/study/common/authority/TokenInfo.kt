//authority: 관한 관련 기능 분류
package study.common.authority

//TokenInfo: 로그인 시 토큰 정보를 담아 클라이언트에게 전달
data class TokenInfo (
    val grantType: String,//JWT 권한 인증 타입
    val accessToken: String,//실제 검증할 토큰
)