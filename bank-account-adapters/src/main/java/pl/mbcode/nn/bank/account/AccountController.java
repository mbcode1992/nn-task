package pl.mbcode.nn.bank.account;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.mbcode.nn.bank.account.dto.AccountDto;
import pl.mbcode.nn.bank.account.dto.CreateAccountCommandDto;
import pl.mbcode.nn.bank.command.CreateAccountCommand;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(
            @RequestBody CreateAccountCommandDto dto
    ) {
        CreateAccountCommand command = AccountMapper.get().toCommand(dto);
        return accountService.create(command);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto getAccount(
            @PathVariable("id") UUID id
    ) {
        Account account = accountService.getAccountById(id);
        return AccountMapper.get().toDto(account);
    }
}
