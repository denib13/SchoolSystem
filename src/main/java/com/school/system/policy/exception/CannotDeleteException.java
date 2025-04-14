package com.school.system.policy.exception;

import com.school.system.exception.ForbiddenException;

public class CannotDeleteException extends ForbiddenException {
    public static final int CODE = 703;

    public CannotDeleteException() {
        super("Cannot delete entity", CODE);
    }

    public CannotDeleteException(String message) {
        super(message, CODE);
    }

    public CannotDeleteException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public CannotDeleteException(Throwable cause) {
        super(cause, CODE);
    }
}
