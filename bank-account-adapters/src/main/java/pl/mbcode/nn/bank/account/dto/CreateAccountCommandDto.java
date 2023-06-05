package pl.mbcode.nn.bank.account.dto;

//@JsonSerialize
public record CreateAccountCommandDto(String ownerName, String ownerSurname, double initialBalanceInPLN) {
}
