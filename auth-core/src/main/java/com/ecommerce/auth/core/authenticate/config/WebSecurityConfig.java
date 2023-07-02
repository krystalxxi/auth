package com.ecommerce.auth.core.authenticate.config;


import com.ecommerce.auth.core.authenticate.domain.service.UserQueryService;
import com.ecommerce.auth.core.authenticate.filter.LoginAuthenticationFailureHandler;
import com.ecommerce.auth.core.authenticate.filter.LoginAuthenticationSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import java.io.PrintWriter;

/**
 * spring security 配置
 */

@Configuration
@Slf4j
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private UserQueryService userQueryService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // 解决跨域问题
                .authorizeHttpRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/**").authenticated()
                .antMatchers("/login").permitAll()
                .and()
                .addFilterBefore(loginFilter(http), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // session认证方式
                .and()
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(401);
                    PrintWriter out = response.getWriter();
                    out.write("登录失败");
                    out.flush();
                });
        return http.build();
    }

    /**
     * 认证管理器
     *
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userQueryService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
        return authenticationManager;
    }

    @Bean
    public UsernamePasswordAuthenticationFilter loginFilter(HttpSecurity httpSecurity) throws Exception {
        UsernamePasswordAuthenticationFilter loginFilter = new UsernamePasswordAuthenticationFilter();
        loginFilter.setAuthenticationSuccessHandler(new LoginAuthenticationSuccessHandler());
        loginFilter.setAuthenticationFailureHandler(new LoginAuthenticationFailureHandler());
        loginFilter.setAuthenticationManager(authenticationManager(httpSecurity));
        loginFilter.setFilterProcessesUrl("/login");
        return loginFilter;
    }

    /**
     * 密码
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
