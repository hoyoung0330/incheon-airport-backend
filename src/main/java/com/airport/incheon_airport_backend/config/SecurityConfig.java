package com.airport.incheon_airport_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /* Spring Security는 기본적으로 모든 요청에 인증 요구
        → 지금 당장 로그인 없이 API 테스트하려면 일단 꺼야 함
        → 나중에 JWT 인증 구현할 때 다시 활성화
    */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 → API 서버에서는 일반적으로 필요 없음
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            ); // 모든 요청 허용 (인증 없이 접근 가능)
            //   개발 초기에만 사용, 나중에 아래처럼 변경 예정:
            //   .requestMatchers("/api/auth/**").permitAll()
            //   .anyRequest().authenticated()
        return http.build();
    }
}