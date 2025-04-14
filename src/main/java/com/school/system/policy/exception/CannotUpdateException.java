package com.school.system.policy.exception;

import com.school.system.exception.ForbiddenException;

public class CannotUpdateException extends ForbiddenException {
    public static final int CODE = 702;

    public CannotUpdateException() {
        super("Cannot update entity", CODE);
    }

    public CannotUpdateException(String message) {
        super(message, CODE);
    }

    public CannotUpdateException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public CannotUpdateException(Throwable cause) {
        super(cause, CODE);
    }
}
