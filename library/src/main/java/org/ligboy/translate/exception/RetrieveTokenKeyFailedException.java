package org.ligboy.translate.exception;

/**
 * @author Ligboy Liu (ligboy@gmail.com).
 */
public class RetrieveTokenKeyFailedException extends Exception {

    public RetrieveTokenKeyFailedException() {
    }

    public RetrieveTokenKeyFailedException(String message) {
        super(message);
    }

    public RetrieveTokenKeyFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetrieveTokenKeyFailedException(Throwable cause) {
        super(cause);
    }

    public RetrieveTokenKeyFailedException(String message, Throwable cause, boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
