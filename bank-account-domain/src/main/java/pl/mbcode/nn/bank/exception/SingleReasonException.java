package pl.mbcode.nn.bank.exception;

public abstract class SingleReasonException extends RuntimeException {

    protected SingleReasonException(String message) {
        super(message);
    }

    protected abstract int statusCode();

    protected abstract String errorCode();

}
