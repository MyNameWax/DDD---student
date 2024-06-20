package cn.rzpt.base.handler;

import cn.rzpt.base.result.ResponseResult;
import cn.rzpt.base.util.GsonUtil;
import cn.rzpt.base.util.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseResult<Object> responseResult = new ResponseResult<>(HttpStatus.FORBIDDEN.value(), "权限不足");
        WebUtils.renderString(response, GsonUtil.beanToJson(responseResult));
    }
}
