package pl.mbcode.nn.bank.account;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.mbcode.nn.bank.command.CreateAccountCommand;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
@Getter
@Builder
class Account {

    private final UUID id = UUID.randomUUID();
    private final Owner owner;

    private final Map<Currency, BigDecimal> balances;

    public static Account create(CreateAccountCommand command) {
        log.info("Creating account with initial parameters {}", command.toString());
        Owner owner = new Owner(
                command.getOwnerName(),
                command.getOwnerSurname()
        );
        return Account.builder()
                .owner(owner)
                .balances(Map.of(Currency.PLN, command.getInitialBalanceInPLN()))
                .build();
    }
}
