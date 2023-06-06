package pl.mbcode.nn.bank.account;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class AccountTestConfiguration {

    @Bean
    AccountService accountService(AccountRepository accountRepository) {
        return AccountService.builder()
                .accountRepository(accountRepository)
                .exchangeRateProvider((oldCurrency, newCurrency) -> 5.0)
                .build();
    }

    @Bean
    AccountRepository accountRepository() {
        return new InMemoryAccountRepository();
    }

}