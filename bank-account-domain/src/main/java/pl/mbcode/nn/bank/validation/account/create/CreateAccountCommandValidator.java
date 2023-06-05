package pl.mbcode.nn.bank.validation.account.create;


import lombok.NoArgsConstructor;
import pl.mbcode.nn.bank.command.CreateAccountCommand;
import pl.mbcode.nn.bank.validation.ValidationRule;
import pl.mbcode.nn.bank.validation.Validator;

import java.util.List;

@NoArgsConstructor
public class CreateAccountCommandValidator implements Validator<CreateAccountCommand> {

    @Override
    public List<ValidationRule<CreateAccountCommand>> getRules() {
        return List.of(
                new AccountMustHaveOwnerRule(),
                new InitialBalanceMustBePositiveRule()
        );
    }
}
