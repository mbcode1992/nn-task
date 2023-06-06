package pl.mbcode.nn.bank.account;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import pl.mbcode.nn.bank.integration.nbp.NbpClient;
import pl.mbcode.nn.bank.integration.nbp.model.RateDto;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
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
        if (BASE_RATES_CURRENCY.equals(newCurrency)) {
            return exchangeRatesMap.get(oldCurrency);
        }
        return 1.0 / exchangeRatesMap.get(newCurrency);
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
        List<RateDto> rateDtoList = nbpClient.getRates().get(0).rates();
        rateDtoList.stream()
                .filter(rateDto -> IMPLEMENTED_RATES.contains(rateDto.code()))
                .forEach(rateDto -> exchangeRatesMap.put(Currency.valueOf(rateDto.code()), rateDto.exchangeRate()));
    }
}
