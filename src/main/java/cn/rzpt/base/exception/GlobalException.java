package cn.rzpt.base.exception;


import cn.hutool.core.util.StrUtil;
import cn.rzpt.base.result.Result;
import cn.rzpt.base.result.Results;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Component
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result VaildParamsException(HttpServletRequest request, IllegalArgumentException ex) {
        log.error("[{}] {} {}", request.getMethod(), getUrl(request), ex.getMessage());
        return Results.failure(String.valueOf(ResponseCode.FAIL.getCode()), ex.getMessage());
    }

    @ExceptionHandler(value = BusinessException.class)
    public Result businessException(HttpServletRequest request, BusinessException ex) {
        log.error("[{}] {} {}", request.getMethod(), getUrl(request), ex.getMessage());
        return Results.failure(String.valueOf(ResponseCode.FAIL.getCode()), ex.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public Result runTimeException(HttpServletRequest request, RuntimeException ex) {
        log.error("[{}] {} {}", request.getMethod(), getUrl(request), ex.getMessage());
        return Results.failure(String.valueOf(ResponseCode.FAIL.getCode()), ResponseCode.FAIL.getMessage());
    }


    private String getUrl(HttpServletRequest request) {
        if (StrUtil.isEmpty(request.getQueryString())) {
            return request.getRequestURL().toString();
        }
        return StrUtil.format("{}?{}", request.getRequestURL().toString(), request.getQueryString());
    }

}
