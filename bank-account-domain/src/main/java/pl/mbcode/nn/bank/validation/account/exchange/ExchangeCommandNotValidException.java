package pl.mbcode.nn.bank.validation.account.exchange;


import pl.mbcode.nn.bank.exception.SingleReasonException;

public class ExchangeCommandNotValidException extends SingleReasonException {

    private final String errorCode;

    protected ExchangeCommandNotValidException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    @Override
    protected int statusCode() {
        return 400;
    }

    @Override
    protected String errorCode() {
        return errorCode;
    }

}
