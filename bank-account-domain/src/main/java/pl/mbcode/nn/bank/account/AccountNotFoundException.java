package pl.mbcode.nn.bank.account;

import pl.mbcode.nn.bank.exception.SingleReasonException;

import java.util.UUID;

class AccountNotFoundException extends SingleReasonException {
    private AccountNotFoundException(String message) {
        super(message);
    }

    static AccountNotFoundException forId(UUID uuid) {
        return new AccountNotFoundException(String.format("Account not found for id %s", uuid));
    }

    @Override
    protected int statusCode() {
        return 404;
    }

    @Override
    protected String errorCode() {
        return "ACCOUNT_NOT_FOUND";
    }
}
