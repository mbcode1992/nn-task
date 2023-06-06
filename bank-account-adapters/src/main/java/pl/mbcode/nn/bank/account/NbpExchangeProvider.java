package pl.mbcode.nn.bank.account;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import pl.mbcode.nn.bank.integration.nbp.NbpClient;
import pl.mbcode.nn.bank.integration.nbp.model.RateDto;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Slf4j
class NbpExchangeProvider implements ExchangeRateProvider {

    private static final Currency BASE_RATES_CURRENCY = Currency.PLN;
    private static final List<String> IMPLEMENTED_RATES = Arrays.stream(Currency.values())
            .map(Currency::toString)
            .toList();

    private static final Map<Currency, Double> exchangeRatesMap = new EnumMap<>(Currency.class);

    @NonNull
    private final NbpClient nbpClient;

    @Override
    public double getExchangeRate(Currency oldCurrency, Currency newCurrency) {
        double resolvedRate = BASE_RATES_CURRENCY.equals(newCurrency)
                ? exchangeRatesMap.get(oldCurrency)
                : 1.0 / exchangeRatesMap.get(newCurrency);
        log.info("Resolved exchange rate from {} to {} is {}", oldCurrency, newCurrency, resolvedRate);
        return resolvedRate;
    }

    @PostConstruct
    void initRates() {
        refreshRates();
    }

    @Scheduled(cron = "0 0 12 * * ?")
    void scheduleRefreshRates() {
        refreshRates();
    }

    void refreshRates() {
        log.info("Refreshing exchange rates");
        List<RateDto> rateDtoList = nbpClient.getRates().get(0).rates();
        rateDtoList.stream()
                .filter(rateDto -> IMPLEMENTED_RATES.contains(rateDto.code()))
                .forEach(rateDto -> exchangeRatesMap.put(Currency.valueOf(rateDto.code()), rateDto.exchangeRate()));
    }
}
