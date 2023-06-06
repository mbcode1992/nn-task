package pl.mbcode.nn.bank.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pl.mbcode.nn.bank.account.dto.AccountDto;
import pl.mbcode.nn.bank.exception.RestExceptionHandler;
import pl.mbcode.nn.bank.utils.FileReader;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {
        AccountController.class,
        AccountTestConfiguration.class,
        RestExceptionHandler.class
})
class CreateAccountTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("Should create account")
    void shouldCreateAccount() throws Exception {
        //given
        String request = FileReader.readResource("createAccount/1-TC-request.json");

        //when
        String result = mockMvc.perform(
                        post("/accounts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        AccountDto accountDto = mapper.readValue(result, AccountDto.class);

        //then
        assertAll(
                () -> assertNotNull(accountDto.id()),
                () -> assertEquals("testName", accountDto.owner().name()),
                () -> assertEquals("testSurname", accountDto.owner().surname()),
                () -> assertEquals("PLN", accountDto.balances().get(0).currency().name()),
                () -> assertEquals(150.00, accountDto.balances().get(0).amount().doubleValue())
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "createAccount/2-TC-error-request.json",
            "createAccount/3-TC-error-request.json",
            "createAccount/4-TC-error-request.json",
            "createAccount/5-TC-error-request.json"
    })
    @DisplayName("Should not create account")
    void shouldNotCreateAccount(String requestPath) throws Exception {
        //given
        String request = FileReader.readResource(requestPath);

        //when
        //then
        mockMvc.perform(
                        post("/accounts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                .andExpect(status().isBadRequest());
    }

}
