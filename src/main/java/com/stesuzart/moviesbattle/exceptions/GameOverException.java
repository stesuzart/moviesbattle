package com.stesuzart.moviesbattle.exceptions;

public class GameOverException extends RuntimeException {
    public GameOverException() {
        this("The player has reached the maximum amount of errors.", null);
    }
    public GameOverException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }

}
