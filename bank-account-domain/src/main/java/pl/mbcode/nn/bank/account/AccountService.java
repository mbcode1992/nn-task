package pl.mbcode.nn.bank.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.mbcode.nn.bank.command.CreateAccountCommand;
import pl.mbcode.nn.bank.command.ExchangeMoneyCommand;
import pl.mbcode.nn.bank.validation.account.create.CreateAccountCommandValidator;
import pl.mbcode.nn.bank.validation.account.exchange.ExchangeCommandValidator;

import java.util.UUID;

@Slf4j
@Builder
@AllArgsConstructor
public class AccountService {

    @NonNull
    private final AccountRepository accountRepository;

    @NonNull
    private final ExchangeRateProvider exchangeRateProvider;

    public Account create(CreateAccountCommand command) {
        log.info("Creating account method invoked");
        CreateAccountCommandValidator validator = new CreateAccountCommandValidator();
        validator.validate(command);
        Account account = accountRepository.save(Account.create(command));
        log.info("Account created successfully with id {}", account.getId());
        return account;
    }

    public Account getAccountById(UUID id) {
        log.info("Getting account with id {}", id);
        return accountRepository.getById(id);
    }

    public Account exchangeMoney(ExchangeMoneyCommand command) {
        log.info("Exchange money method invoked for account {}", command.getAccountId());
        ExchangeCommandValidator validator = new ExchangeCommandValidator();
        validator.validate(command);
        Account account = accountRepository.getById(command.getAccountId());
        double exchangeRate = exchangeRateProvider.getExchangeRate(command.getOldCurrency(), command.getNewCurrency());
        account.exchangeMoney(command, exchangeRate);
        accountRepository.save(account);
        return account;
    }
}
