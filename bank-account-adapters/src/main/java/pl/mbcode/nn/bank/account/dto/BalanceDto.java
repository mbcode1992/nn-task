package pl.mbcode.nn.bank.account.dto;

import pl.mbcode.nn.bank.account.Currency;

import java.math.BigDecimal;

public record BalanceDto(Currency currency, BigDecimal amount) {
}
