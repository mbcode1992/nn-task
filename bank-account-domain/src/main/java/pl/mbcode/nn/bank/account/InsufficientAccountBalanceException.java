package pl.mbcode.nn.bank.account;

import pl.mbcode.nn.bank.exception.SingleReasonException;

import java.math.BigDecimal;

class InsufficientAccountBalanceException extends SingleReasonException {
    public InsufficientAccountBalanceException(BigDecimal currentBalance) {
        super(String.format("You account balance is %.2f and it insufficient to make this exchange", currentBalance));
    }

    @Override
    protected int statusCode() {
        return 402;
    }

    @Override
    protected String errorCode() {
        return "INSUFFICIENT_ACCOUNT_BALANCE";
    }
}
