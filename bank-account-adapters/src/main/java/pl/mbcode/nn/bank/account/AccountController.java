package pl.mbcode.nn.bank.account;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.mbcode.nn.bank.account.dto.AccountDto;
import pl.mbcode.nn.bank.account.dto.CreateAccountCommandDto;
import pl.mbcode.nn.bank.account.dto.ExchangeMoneyCommandDto;
import pl.mbcode.nn.bank.command.CreateAccountCommand;
import pl.mbcode.nn.bank.command.ExchangeMoneyCommand;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(
            @RequestBody CreateAccountCommandDto dto
    ) {
        CreateAccountCommand command = AccountMapper.get().toCommand(dto);
        Account account = accountService.create(command);
        return AccountMapper.get().toDto(account);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto getAccount(
            @PathVariable("id") UUID id
    ) {
        Account account = accountService.getAccountById(id);
        return AccountMapper.get().toDto(account);
    }

    @PostMapping("/{id}/exchange")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto exchangeMoney(
            @PathVariable("id") UUID id,
            @RequestBody ExchangeMoneyCommandDto dto
    ) {
        ExchangeMoneyCommand command = AccountMapper.get().toCommand(dto, id);
        Account account = accountService.exchangeMoney(command);
        return AccountMapper.get().toDto(account);
    }
}
