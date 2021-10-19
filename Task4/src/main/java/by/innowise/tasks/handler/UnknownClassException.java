package by.innowise.tasks.handler;

public class UnknownClassException extends RuntimeException{
    public UnknownClassException() {
    }

    public UnknownClassException(String message) {
        super(message);
    }

    public UnknownClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownClassException(Throwable cause) {
        super(cause);
    }

    public UnknownClassException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
