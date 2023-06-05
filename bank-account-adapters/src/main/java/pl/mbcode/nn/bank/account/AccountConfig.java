package pl.mbcode.nn.bank.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AccountConfig {

    @Bean
    AccountService accountService(AccountRepository accountRepository) {
        return AccountService.builder()
                .accountRepository(accountRepository)
                .build();
    }

    @Bean
    AccountRepository accountRepository() {
        return new InMemoryAccountRepository();
    }
}
