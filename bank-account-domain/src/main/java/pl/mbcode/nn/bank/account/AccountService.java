package pl.mbcode.nn.bank.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.mbcode.nn.bank.command.CreateAccountCommand;
import pl.mbcode.nn.bank.validation.account.create.CreateAccountCommandValidator;

@Slf4j
@Builder
@AllArgsConstructor
public class AccountService {

    @NonNull
    private final AccountRepository accountRepository;

    public Account create(CreateAccountCommand command) {
        log.info("Creating account with initial parameters {}", command.toString());
        CreateAccountCommandValidator validator = new CreateAccountCommandValidator();
        validator.validate(command);
        Account account = accountRepository.save(Account.create(command));
        log.info("Account created successfully with id {}", account.getId());
        return account;
    }

}
