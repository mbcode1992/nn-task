package pl.mbcode.nn.bank.validation.account.exchange;

import pl.mbcode.nn.bank.command.ExchangeMoneyCommand;
import pl.mbcode.nn.bank.validation.ValidationRule;

class CurrencyMustBeDifferent implements ValidationRule<ExchangeMoneyCommand> {
    @Override
    public void validate(ExchangeMoneyCommand input) {
        if (input.getOldCurrency() == input.getNewCurrency()) {
            throw new ExchangeCommandNotValidException("SAME_CURRENCY", "Currency must be different");
        }
    }
}
