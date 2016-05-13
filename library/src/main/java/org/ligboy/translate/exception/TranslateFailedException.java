package org.ligboy.translate.exception;

/**
 * @author Ligboy Liu (ligboy@gmail.com).
 */
public class TranslateFailedException extends Exception {

    public TranslateFailedException() {
    }

    public TranslateFailedException(String message) {
        super(message);
    }

    public TranslateFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TranslateFailedException(Throwable cause) {
        super(cause);
    }

    public TranslateFailedException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
