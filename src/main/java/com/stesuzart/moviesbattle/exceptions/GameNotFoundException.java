package com.stesuzart.moviesbattle.exceptions;

public class GameNotFoundException extends NotFoundException {
    public GameNotFoundException() {
        this("O Game nao foi encontrado", null);
    }
    public GameNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
