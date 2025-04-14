package com.school.system.policy.exception;

import com.school.system.exception.ForbiddenException;

public class CannotReadException extends ForbiddenException {
    public static final int CODE = 701;

    public CannotReadException() {
        super("Cannot read entity", CODE);
    }

    public CannotReadException(String message) {
        super(message, CODE);
    }

    public CannotReadException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public CannotReadException(Throwable cause) {
        super(cause, CODE);
    }
}
