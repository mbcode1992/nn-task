package pl.mbcode.nn.bank.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pl.mbcode.nn.bank.command.CreateAccountCommand;
import pl.mbcode.nn.bank.validation.account.create.CreateCommandNotValidException;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private final AccountService accountService = AccountService.builder()
            .accountRepository(new InMemoryAccountRepository())
            .build();

    @Test
    @DisplayName("Account should be created successfully")
    void create() {
//        given
        CreateAccountCommand command = CreateAccountCommand.builder()
                .initialBalanceInPLN(BigDecimal.ONE)
                .ownerName("properName")
                .ownerSurname("properSurname")
                .build();
//        when
        Account account = accountService.create(command);

//        then
        assertAll(
                () -> assertNotNull(account.getId()),
                () -> assertEquals("properName", account.getOwner().name()),
                () -> assertEquals("properSurname", account.getOwner().surname()),
                () -> assertEquals(1.0, account.getBalances().get(Currency.PLN).doubleValue())
        );
    }

    @ParameterizedTest()
    @DisplayName("Create account command not valid exception should be thrown")
    @MethodSource("invalidCreateAccountCommands")
    void invalidCreateCommand(CreateAccountCommand command) {
//        given
//        when
        Executable executable = () -> accountService.create(command);

//        then
        assertThrows(CreateCommandNotValidException.class, executable);

    }

    private static Stream<CreateAccountCommand> invalidCreateAccountCommands() {
        return Stream.of(
                CreateAccountCommand.builder().build(),
                CreateAccountCommand.builder()
                        .ownerName("value")
                        .build(),
                CreateAccountCommand.builder()
                        .ownerSurname("value")
                        .build(),
                CreateAccountCommand.builder()
                        .initialBalanceInPLN(BigDecimal.ONE)
                        .build(),
                CreateAccountCommand.builder()
                        .ownerName("value")
                        .ownerSurname("value")
                        .initialBalanceInPLN(BigDecimal.valueOf(-0.1))
                        .build()
        );
    }
}