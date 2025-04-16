//SecurityConfig: 인증, 인가 관리
package study.common.authority

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Bean//스프링 컨테이너를 통해 관리되는 객체
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            //JWT 를 사용하기 때문에 Session 사용 X
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            //권한 관리
            //인증되지 않은 사용자만 "/api/member/signup, login" URL 호출 가능
            //그 외의 요청은 회원 권한이 있어야 가능
            .authorizeHttpRequests {
                it.requestMatchers("/api/member/signup", "/api/member/login").anonymous()
                    .requestMatchers("/api/member/info/**").hasRole("MEMBER")
                    .anyRequest().permitAll()
            }
            //뒤 필터를 실행하기 전에 앞 필터를 먼저 실행, 앞 필터가 통과되면 뒤 필터는 실행 X
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }

    //코드 암호화
    @Bean
    fun passwordEncoder(): PasswordEncoder =
        PasswordEncoderFactories.createDelegatingPasswordEncoder()
}