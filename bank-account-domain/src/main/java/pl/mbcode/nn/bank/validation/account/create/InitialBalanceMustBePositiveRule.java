package pl.mbcode.nn.bank.validation.account.create;

import pl.mbcode.nn.bank.command.CreateAccountCommand;
import pl.mbcode.nn.bank.validation.ValidationRule;

import java.math.BigDecimal;

class InitialBalanceMustBePositiveRule implements ValidationRule<CreateAccountCommand> {
    @Override
    public void validate(CreateAccountCommand input) {
        if (BigDecimal.ZERO.compareTo(input.getInitialBalanceInPLN()) >= 0) {
            throw new CreateCommandNotValidException("INVALID_OWNER", "Owner must have both name and surname");
        }
    }
}
