package pl.mbcode.nn.bank.account.dto;

import pl.mbcode.nn.bank.account.Owner;

import java.util.List;

public record AccountDto(Owner owner, List<BalanceDto> balances) {
}
