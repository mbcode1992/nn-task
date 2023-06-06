package pl.mbcode.nn.bank.integration.nbp.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record RateDto(String code, @JsonAlias("mid") double exchangeRate) {
}
