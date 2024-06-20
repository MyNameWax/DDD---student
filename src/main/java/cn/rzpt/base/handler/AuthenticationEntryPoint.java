package cn.rzpt.base.handler;

import cn.rzpt.base.result.ResponseResult;
import cn.rzpt.base.util.GsonUtil;
import cn.rzpt.base.util.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseResult<Object> responseResult = new ResponseResult<>(HttpStatus.UNAUTHORIZED.value(), "请先登录");
        WebUtils.renderString(response, GsonUtil.beanToJson(responseResult));
    }
}
