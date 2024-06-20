package cn.rzpt.base.util;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

public class WebUtils {
    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     */
    @SneakyThrows
    public static void renderString(HttpServletResponse response, String string) {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(string);
    }
}
