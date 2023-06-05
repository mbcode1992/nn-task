package pl.mbcode.nn.bank.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@Getter
@ToString
public class CreateAccountCommand {

    private final String ownerName;
    private final String ownerSurname;
    private final BigDecimal initialBalanceInPLN;

}
