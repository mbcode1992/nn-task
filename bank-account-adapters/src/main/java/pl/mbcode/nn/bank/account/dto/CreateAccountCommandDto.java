package pl.mbcode.nn.bank.account.dto;

public record CreateAccountCommandDto(String ownerName, String ownerSurname, double initialBalanceInPLN) {
}
