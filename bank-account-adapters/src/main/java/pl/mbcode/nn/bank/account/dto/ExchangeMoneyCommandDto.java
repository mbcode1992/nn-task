package pl.mbcode.nn.bank.account.dto;

import pl.mbcode.nn.bank.account.Currency;

public record ExchangeMoneyCommandDto(Currency oldCurrency, Currency newCurrency, double oldCurrencyAmount) {
}
