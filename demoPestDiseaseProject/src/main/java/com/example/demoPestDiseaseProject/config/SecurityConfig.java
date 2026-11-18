package com.example.demoPestDiseaseProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정.
 *
 * <p>로그인은 구글 OAuth2 로그인(spring-boot-starter-security-oauth2-client)을 쓴다.
 * 구글 클라이언트 정보는 application.properties 의
 * spring.security.oauth2.client.registration.google.* 에서 읽는다.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // shortcut: 프론트가 JS(fetch)로 POST 하는 구조라 CSRF 를 껐다. 세션 쿠키 로그인이므로 배포 전 CSRF 토큰 방식으로 바꿀 것
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // 관리자 전용 API. ROLE_ADMIN 부여는 member.role 을 읽는 OAuth2UserService 를 만든 뒤에 동작한다
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // 그 밖의 API 는 로그인 필요
                        .requestMatchers("/api/**").authenticated()
                        // 정적 파일, 로그인 페이지, /error 등은 모두 허용
                        .anyRequest().permitAll()
                )

                // 구글 로그인: /oauth2/authorization/google 로 이동하면 로그인 시작
                .oauth2Login(Customizer.withDefaults())

                // POST /logout 으로 세션 종료 후 / 로 이동
                .logout(logout -> logout.logoutSuccessUrl("/"));

        return http.build();
    }
}
