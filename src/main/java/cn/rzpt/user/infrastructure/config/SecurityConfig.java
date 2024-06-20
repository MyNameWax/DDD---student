package cn.rzpt.user.infrastructure.config;

import cn.rzpt.base.handler.AccessDeniedHandler;
import cn.rzpt.base.handler.AuthenticationEntryPoint;
import cn.rzpt.user.application.filter.TokenFilter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenFilter tokenFilter;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationConfiguration authenticationConfiguration;

    /**
     * 配置密码加密器
     *
     * @return 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Http安全配置过滤器
     *
     * @param httpSecurity httpSecurity对象
     * @return httpSecurity对象
     */
    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) {
        httpSecurity.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.authorizeHttpRequests(request -> {
            request.requestMatchers("/user/register", "/user/login").permitAll()
                    .anyRequest().authenticated();
        });
        // 设置token过期率放到UsernamePasswordAuthorizationFilter过滤器之前
        httpSecurity.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 权限处理器
        httpSecurity.exceptionHandling(accessDeniedHandler -> {
            accessDeniedHandler.accessDeniedHandler(this.accessDeniedHandler);
        });
        // 认证处理器
        httpSecurity.exceptionHandling(authenticationConfiguration -> {
            authenticationConfiguration.authenticationEntryPoint(this.authenticationEntryPoint);
        });
        httpSecurity.cors(AbstractHttpConfigurer::disable);
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    /**
     * 认证管理器
     *
     * @return 认证管理器对象
     */
    @Bean
    @SneakyThrows
    public AuthenticationManager authenticationManager() {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
