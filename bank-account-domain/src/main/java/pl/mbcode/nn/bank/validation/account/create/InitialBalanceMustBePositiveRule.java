package pl.mbcode.nn.bank.validation.account.create;

import pl.mbcode.nn.bank.command.CreateAccountCommand;
import pl.mbcode.nn.bank.validation.ValidationRule;

import java.math.BigDecimal;
import java.util.Objects;

class InitialBalanceMustBePositiveRule implements ValidationRule<CreateAccountCommand> {
    @Override
    public void validate(CreateAccountCommand input) {
        if (Objects.isNull(input.getInitialBalanceInPLN()) || BigDecimal.ZERO.compareTo(input.getInitialBalanceInPLN()) >= 0) {
            throw new CreateCommandNotValidException("INVALID_BALANCE", "Initial balance must be positive");
        }
    }
}
