package cn.rzpt.base.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;


@EqualsAndHashCode(callSuper = true)
public class ValidationException extends BusinessException {

    @Getter
    private Object[] params;

    public ValidationException(String message) {
        super(500, message);
    }

    public ValidationException(String message, Object[] params) {
        super(500,message);
        this.params = params;
    }

    public ValidationException(String code, String message, Object[] params) {
        super(Integer.valueOf(code), message);
        this.params = params;
    }

    public static ValidationException of(String code, Object[] params) {
        return new ValidationException(code, null, params);
    }

}
