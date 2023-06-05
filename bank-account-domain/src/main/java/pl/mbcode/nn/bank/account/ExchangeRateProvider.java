package pl.mbcode.nn.bank.account;

interface ExchangeRateProvider {
    double getExchangeRate(Currency oldCurrency, Currency newCurrency);
}
