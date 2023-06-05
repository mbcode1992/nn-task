package pl.mbcode.nn.bank.validation.account.exchange;

import pl.mbcode.nn.bank.account.Currency;
import pl.mbcode.nn.bank.command.ExchangeMoneyCommand;
import pl.mbcode.nn.bank.validation.ValidationRule;

class OneOfCurrencyMustBePln implements ValidationRule<ExchangeMoneyCommand> {
    @Override
    public void validate(ExchangeMoneyCommand input) {
        if(Currency.PLN.equals(input.getOldCurrency()) || Currency.PLN.equals(input.getNewCurrency())){
            return;
        }
        throw new ExchangeCommandNotValidException("MISSING_PLN_CURRENCY", "One of currency must be PLN");
    }
}
