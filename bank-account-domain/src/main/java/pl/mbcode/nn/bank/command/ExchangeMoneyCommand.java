package pl.mbcode.nn.bank.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.mbcode.nn.bank.account.Currency;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@ToString
public class ExchangeMoneyCommand {

    private final UUID accountId;
    private final BigDecimal amount;
    private final Currency oldCurrency;
    private final Currency newCurrency;

}
