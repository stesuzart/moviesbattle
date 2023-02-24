package com.stesuzart.moviesbattle.exceptions;

public class RoundNotFoundException extends NotFoundException {
    public RoundNotFoundException() {
        this("The Round was not found.", null);
    }
    public RoundNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
