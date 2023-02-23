package com.stesuzart.moviesbattle.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }

}
