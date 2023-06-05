package pl.mbcode.nn.bank.account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.mbcode.nn.bank.command.CreateAccountCommand;
import pl.mbcode.nn.bank.command.ExchangeMoneyCommand;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
@Getter
@Builder
class Account {

    private final UUID id = UUID.randomUUID();
    private final Owner owner;

    private final Map<Currency, BigDecimal> balances;

    protected static Account create(CreateAccountCommand command) {
        log.debug("Creating account with initial parameters {}", command.toString());
        Owner owner = new Owner(
                command.getOwnerName(),
                command.getOwnerSurname()
        );
        Map<Currency, BigDecimal> tempMap = Map.of(Currency.PLN, command.getInitialBalanceInPLN().setScale(2, RoundingMode.HALF_UP));
        return Account.builder()
                .owner(owner)
                .balances(new HashMap<>(tempMap))
                .build();
    }

    protected void exchangeMoney(ExchangeMoneyCommand command, double exchangeRate) {
        log.debug("Exchanging money with following parameters {} and exchangeRate {} ", command.toString(), exchangeRate);
        BigDecimal currentBalance = balances.getOrDefault(command.getOldCurrency(), BigDecimal.ZERO);
        if (currentBalance.compareTo(command.getAmount()) <= 0) {
            throw new InsufficientAccountBalanceException(currentBalance);
        }
        BigDecimal exchangedAmount = command.getAmount()
                .multiply(BigDecimal.valueOf(exchangeRate))
                .setScale(2, RoundingMode.HALF_UP);
        addBalance(command.getNewCurrency(), exchangedAmount);
        reduceBalance(command.getOldCurrency(), command.getAmount());
    }

    private void reduceBalance(Currency currency, BigDecimal amount) {
        BigDecimal currentBalance = balances.getOrDefault(currency, BigDecimal.ZERO);
        BigDecimal newBalance = currentBalance.subtract(amount);
        balances.put(currency, newBalance);
    }

    private void addBalance(Currency currency, BigDecimal amount) {
        BigDecimal currentBalance = balances.getOrDefault(currency, BigDecimal.ZERO);
        BigDecimal newBalance = currentBalance.add(amount);
        balances.put(currency, newBalance);
    }

}
