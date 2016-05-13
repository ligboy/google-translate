package org.ligboy.translate.exception;

/**
 * @author Ligboy Liu (ligboy@gmail.com).
 */
public class IllegalTokenKeyException extends Exception {

    public IllegalTokenKeyException() {
    }

    public IllegalTokenKeyException(String message) {
        super(message);
    }

    public IllegalTokenKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalTokenKeyException(Throwable cause) {
        super(cause);
    }

    public IllegalTokenKeyException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
