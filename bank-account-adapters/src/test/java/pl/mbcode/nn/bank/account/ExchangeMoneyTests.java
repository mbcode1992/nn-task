package pl.mbcode.nn.bank.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pl.mbcode.nn.bank.account.dto.AccountDto;
import pl.mbcode.nn.bank.account.dto.BalanceDto;
import pl.mbcode.nn.bank.exception.RestExceptionHandler;
import pl.mbcode.nn.bank.utils.FileReader;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {
        AccountController.class,
        AccountTestConfiguration.class,
        RestExceptionHandler.class
})
class ExchangeMoneyTests {

    private static String accountId;
    private static final Map<Currency, BigDecimal> balanceMap = Map.of(
            Currency.PLN, BigDecimal.valueOf(100.00),
            Currency.USD, BigDecimal.valueOf(100.00)
    );

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private InMemoryAccountRepository accountRepository;

    @BeforeEach
    void prepareRepository() {
        accountRepository.clear();

        Account account = Account.builder()
                .owner(new Owner("testName", "testSurname"))
                .balances(new HashMap<>(balanceMap))
                .build();
        accountId = account.getId().toString();
        accountRepository.save(account);
    }

    @Test
    @DisplayName("Should exchange money")
    void shouldExchangeMoney() throws Exception {
        //given
        String request = FileReader.readResource("exchangeMoney/1-TC-request.json");

        //when
        String result = mockMvc.perform(
                        post("/accounts/{id}/exchange", accountId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        AccountDto accountDto = mapper.readValue(result, AccountDto.class);

        //then
        BalanceDto plnBalance = accountDto.balances().stream()
                .filter(balanceDto -> Currency.PLN.equals(balanceDto.currency()))
                .findFirst().get();
        BalanceDto usdBalance = accountDto.balances().stream()
                .filter(balanceDto -> Currency.USD.equals(balanceDto.currency()))
                .findFirst().get();
        assertAll(
                () -> assertEquals(90.00, plnBalance.amount().doubleValue()),
                () -> assertEquals(150.00, usdBalance.amount().doubleValue())
        );
    }

    @ParameterizedTest
    @CsvSource({
            "exchangeMoney/2-TC-error-request.json, 400",
            "exchangeMoney/3-TC-error-request.json, 402",
            "exchangeMoney/4-TC-error-request.json, 400",
    })
    @DisplayName("Should not exchange money")
    void shouldNotExchangeMoney(String requestPath, int expectedStatus) throws Exception {
        //given
        String request = FileReader.readResource(requestPath);

        //when
        //then
        mockMvc.perform(
                        post("/accounts/{id}/exchange", accountId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                .andExpect(status().is(expectedStatus));
    }

}
