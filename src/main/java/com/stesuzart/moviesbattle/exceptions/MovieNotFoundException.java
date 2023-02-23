package com.stesuzart.moviesbattle.exceptions;

public class MovieNotFoundException extends NotFoundException {
    public MovieNotFoundException() {
        this("O Filme nao foi encontrado", null);
    }
    public MovieNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
