package pl.mbcode.nn.bank.validation.account.create;

import pl.mbcode.nn.bank.command.CreateAccountCommand;
import pl.mbcode.nn.bank.utils.DataUtils;
import pl.mbcode.nn.bank.validation.ValidationRule;

class AccountMustHaveOwnerRule implements ValidationRule<CreateAccountCommand> {
    @Override
    public void validate(CreateAccountCommand input) {
        if (DataUtils.isBlank(input.getOwnerName()) || DataUtils.isBlank(input.getOwnerSurname())) {
            throw new CreateCommandNotValidException("INVALID_OWNER", "Owner must have both name and surname");
        }
    }
}
