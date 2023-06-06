package pl.mbcode.nn.bank.validation.account.exchange;

import pl.mbcode.nn.bank.command.ExchangeMoneyCommand;
import pl.mbcode.nn.bank.validation.ValidationRule;

import java.math.BigDecimal;

class AmountMustBePositive implements ValidationRule<ExchangeMoneyCommand> {
    @Override
    public void validate(ExchangeMoneyCommand input) {
        if (BigDecimal.ZERO.compareTo(input.getAmount()) > 0) {
            throw new ExchangeCommandNotValidException("POSITIVE_AMOUNT_REQUIRED", "Amount must be positive number");
        }
    }
}
