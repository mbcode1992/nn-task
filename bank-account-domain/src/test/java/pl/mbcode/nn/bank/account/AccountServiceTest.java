package pl.mbcode.nn.bank.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pl.mbcode.nn.bank.command.CreateAccountCommand;
import pl.mbcode.nn.bank.command.ExchangeMoneyCommand;
import pl.mbcode.nn.bank.validation.account.create.CreateCommandNotValidException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private final InMemoryAccountRepository accountRepository = new InMemoryAccountRepository();

    private final AccountService accountService = AccountService.builder()
            .accountRepository(accountRepository)
            .exchangeRateProvider((oldCurrency, newCurrency) -> 4.1933)
            .build();

    @BeforeEach
    void before() {
        accountRepository.clear();
    }

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
                () -> assertEquals(1.00, account.getBalances().get(Currency.PLN).doubleValue())
        );
    }

    @Test
    @DisplayName("Account should be created successfully")
    void createAndRound() {
//        given
        CreateAccountCommand command = CreateAccountCommand.builder()
                .initialBalanceInPLN(BigDecimal.valueOf(2.819123123))
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
                () -> assertEquals(2.82, account.getBalances().get(Currency.PLN).doubleValue())
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

    @Test
    @DisplayName("Should return account by id")
    void getById() {
//        given
        Account account = Account.builder()
                .owner(new Owner("name", "surname"))
                .balances(Map.of(Currency.USD, BigDecimal.TWO))
                .build();
        accountRepository.save(account);

//        when
        Account savedAccount = accountService.getAccountById(account.getId());

//        then
        assertNotNull(savedAccount);
    }

    @Test
    @DisplayName("Should throw account not found exception")
    void accountNotFound() {
//        given
        UUID id = UUID.randomUUID();

//        when
        Executable executable = () -> accountService.getAccountById(id);

//        then
        assertThrows(AccountNotFoundException.class, executable);
    }

    @Test
    @DisplayName("Should exchange money")
    void successExchange() {
//        given
        Account account = Account.builder()
                .owner(new Owner("name", "surname"))
                .balances(new HashMap<>(Map.of(Currency.USD, BigDecimal.TEN)))
                .build();
        accountRepository.save(account);
        ExchangeMoneyCommand command = ExchangeMoneyCommand.builder()
                .oldCurrency(Currency.USD)
                .newCurrency(Currency.PLN)
                .accountId(account.getId())
                .amount(BigDecimal.ONE)
                .build();

//        when
        Account accountAfterExchange = accountService.exchangeMoney(command);

//        then
        assertAll(
                () -> assertEquals(9.00, accountAfterExchange.getBalances().get(Currency.USD).doubleValue()),
                () -> assertEquals(4.19, accountAfterExchange.getBalances().get(Currency.PLN).doubleValue())
        );
    }
}