package cn.rzpt.user.application.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.rzpt.base.exception.BusinessException;
import cn.rzpt.base.util.JwtUtil;
import cn.rzpt.base.util.RedisCache;
import cn.rzpt.user.domain.constant.UserConstant;
import cn.rzpt.user.infrastructure.repository.po.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static cn.rzpt.base.exception.ResponseCode.NOT_AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StrUtil.isEmpty(request.getHeader("Authorization"))) {
            filterChain.doFilter(request, response);
            return;
        }
        String userId = jwtUtil.getUserId(request.getHeader("Authorization"));
        if (StrUtil.isEmpty(userId)) {
            throw new BusinessException(NOT_AUTHORIZATION);
        }
        User user = (User) redisCache.getCacheObject(UserConstant.UserLoginConstant.LOGIN_USER_KEY + userId);
        if (ObjectUtil.isNull(user)) {
            throw new BusinessException(NOT_AUTHORIZATION);
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user,null,null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);

    }
}
