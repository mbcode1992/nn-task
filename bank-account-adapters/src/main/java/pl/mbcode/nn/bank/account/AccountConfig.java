package pl.mbcode.nn.bank.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mbcode.nn.bank.integration.nbp.NbpClient;

@Configuration
class AccountConfig {

    @Bean
    AccountService accountService(AccountRepository accountRepository, ExchangeRateProvider exchangeRateProvider) {
        return AccountService.builder()
                .accountRepository(accountRepository)
                .exchangeRateProvider(exchangeRateProvider)
                .build();
    }

    @Bean
    AccountRepository accountRepository() {
        return new InMemoryAccountRepository();
    }

    @Bean
    ExchangeRateProvider exchangeRateProvider(NbpClient nbpClient) {
        return new NbpExchangeProvider(nbpClient);
    }
}
