package com.stesuzart.moviesbattle.exceptions;

public class GameOverException extends RuntimeException {
    public GameOverException() {
        this("O jogador atigingiu a quantidade maxima de erros", null);
    }
    public GameOverException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }

}
