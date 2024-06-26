package cn.rzpt.base.util;


import cn.rzpt.base.exception.ValidationException;

/**
 * 校验工具类
 *
 * @author baiyan
 */
public class ValidationUtil {

    public static void isTrue(boolean expect, String code, Object... params) {
        if (!expect) {
            throw ValidationException.of(code, params);
        }
    }

    public static void isFalse(boolean expect, String code, Object... params) {
        isTrue(!expect, code, params);
    }

}
