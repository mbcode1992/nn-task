package pl.mbcode.nn.bank.account.dto;

import pl.mbcode.nn.bank.account.Owner;

import java.util.List;
import java.util.UUID;

public record AccountDto(UUID id, Owner owner, List<BalanceDto> balances) {
}
