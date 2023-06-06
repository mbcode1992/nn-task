package pl.mbcode.nn.bank.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pl.mbcode.nn.bank.account.dto.AccountDto;
import pl.mbcode.nn.bank.exception.RestExceptionHandler;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {
        AccountController.class,
        AccountTestConfiguration.class,
        RestExceptionHandler.class
})
class GetAccountTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AccountRepository accountRepository;


    @Test
    @DisplayName("Should get account")
    void shouldCreateAccount() throws Exception {
        //given
        Account account = Account.builder()
                .owner(new Owner("testName", "testSurname"))
                .balances(Map.of(
                        Currency.PLN, BigDecimal.TEN,
                        Currency.USD, BigDecimal.ONE
                ))
                .build();
        accountRepository.save(account);

        //when
        String result = mockMvc.perform(
                        get("/accounts/{id}", account.getId().toString()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        AccountDto accountDto = mapper.readValue(result, AccountDto.class);

        //then
        assertAll(
                () -> assertEquals(account.getId().toString(), accountDto.id().toString()),
                () -> assertEquals("testName", accountDto.owner().name()),
                () -> assertEquals("testSurname", accountDto.owner().surname()),
                () -> assertEquals(2, accountDto.balances().size())
        );
    }

    @Test
    @DisplayName("Should not found account")
    void shouldNotReturnAccount() throws Exception {
        //given
        UUID uuid = UUID.randomUUID();

        //when
        //then
        mockMvc.perform(
                        get("/accounts/{id}", uuid.toString()))
                .andExpect(status().isNotFound());


    }

}
