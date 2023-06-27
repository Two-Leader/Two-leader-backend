package com.twoleader.backend.global.config;

import com.twoleader.backend.global.config.security.JwtAccessDeniedHandler;
import com.twoleader.backend.global.config.security.JwtAuthenticationEntryPoint;
import com.twoleader.backend.global.config.security.JwtProvider;
import com.twoleader.backend.global.config.security.JwtSecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.httpBasic().disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
            .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/api/v1/**").permitAll()
                .antMatchers("/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**","/v3/api-docs/swagger-config/**").permitAll()
                .anyRequest()
                    .authenticated()
            .and()
            .apply(new JwtSecurityConfig(jwtProvider));

        return http.build();
    }
}
