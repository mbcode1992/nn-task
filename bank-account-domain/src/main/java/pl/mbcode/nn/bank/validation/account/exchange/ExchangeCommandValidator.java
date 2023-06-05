package pl.mbcode.nn.bank.validation.account.exchange;


import lombok.NoArgsConstructor;
import pl.mbcode.nn.bank.command.ExchangeMoneyCommand;
import pl.mbcode.nn.bank.validation.ValidationRule;
import pl.mbcode.nn.bank.validation.Validator;

import java.util.List;

@NoArgsConstructor
public class ExchangeCommandValidator implements Validator<ExchangeMoneyCommand> {

    @Override
    public List<ValidationRule<ExchangeMoneyCommand>> getRules() {
        return List.of(
                new OneOfCurrencyMustBePln(),
                new AmountMustBePositive()
        );
    }
}
