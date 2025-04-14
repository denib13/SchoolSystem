package com.school.system.exception;

public class ForbiddenException extends RuntimeException {
    private int code;

    public ForbiddenException(int code) {
        this.code = code;
    }
    public ForbiddenException(String message, int code) {
        super(message);
        this.code = code;
    }
    public ForbiddenException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }
    public ForbiddenException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public Object getCode() {
        return this.code;
    }
}
