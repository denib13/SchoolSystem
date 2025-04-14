package com.school.system.policy.exception;

import com.school.system.exception.ForbiddenException;

public class CannotCreateException extends ForbiddenException {
    public static final int CODE = 704;
    public CannotCreateException() {
        super("Cannot create entity", CODE);
    }

    public CannotCreateException(String message) {
        super(message, CODE);
    }

    public CannotCreateException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public CannotCreateException(Throwable cause) {
        super(cause, CODE);
    }
}
