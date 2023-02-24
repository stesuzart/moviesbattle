package com.stesuzart.moviesbattle.exceptions;

public class MovieNotFoundException extends NotFoundException {
    public MovieNotFoundException() {
        this("The Movie was not found.", null);
    }
    public MovieNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
