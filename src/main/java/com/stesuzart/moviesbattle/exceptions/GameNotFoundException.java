package com.stesuzart.moviesbattle.exceptions;

public class GameNotFoundException extends NotFoundException {
    public GameNotFoundException() {
        this("The Game was not found", null);
    }
    public GameNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
