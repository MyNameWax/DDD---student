package cn.rzpt.base.exception;

import lombok.Getter;

@Getter
public enum ResponseCode {

    SUCCESS(200, "SUCCESS"),
    USER_NAME_ALWAYS_EXISTS(300, "用户名已存在"),
    NOT_AUTHORIZATION(401, "未登录"),
    FAIL(500, "系统错误");

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


}
