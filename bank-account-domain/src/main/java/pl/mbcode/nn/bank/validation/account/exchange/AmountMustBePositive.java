package pl.mbcode.nn.bank.validation.account.exchange;

import pl.mbcode.nn.bank.command.ExchangeMoneyCommand;
import pl.mbcode.nn.bank.validation.ValidationRule;

import java.math.BigDecimal;
import java.util.Objects;

class AmountMustBePositive implements ValidationRule<ExchangeMoneyCommand> {
    @Override
    public void validate(ExchangeMoneyCommand input) {
        if (Objects.isNull(input.getOldCurrencyAmount()) || BigDecimal.ZERO.compareTo(input.getOldCurrencyAmount()) >= 0) {
            throw new ExchangeCommandNotValidException("POSITIVE_AMOUNT_REQUIRED", "Amount must be positive number");
        }
    }
}
