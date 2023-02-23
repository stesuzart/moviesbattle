package com.stesuzart.moviesbattle.exceptions;

public class RoundNotFoundException extends NotFoundException {
    public RoundNotFoundException() {
        this("O Round nao foi encontrado", null);
    }
    public RoundNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
