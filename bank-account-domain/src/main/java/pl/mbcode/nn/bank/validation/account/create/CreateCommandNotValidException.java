package pl.mbcode.nn.bank.validation.account.create;


import pl.mbcode.nn.bank.exception.SingleReasonException;

class CreateCommandNotValidException extends SingleReasonException {

    private final String errorCode;

    protected CreateCommandNotValidException(String errorCode, String message) {
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
