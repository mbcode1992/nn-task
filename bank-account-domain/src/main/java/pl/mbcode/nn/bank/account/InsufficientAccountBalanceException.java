package pl.mbcode.nn.bank.account;

import pl.mbcode.nn.bank.exception.SingleReasonException;

import java.math.BigDecimal;

class InsufficientAccountBalanceException extends SingleReasonException {
    public InsufficientAccountBalanceException(BigDecimal currentBalance, Currency oldCurrency) {
        super(String.format("You account balance in %s is %.2f and it insufficient to make this exchange", oldCurrency, currentBalance));
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
