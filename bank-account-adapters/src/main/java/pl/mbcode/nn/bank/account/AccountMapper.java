package pl.mbcode.nn.bank.account;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import pl.mbcode.nn.bank.account.dto.AccountDto;
import pl.mbcode.nn.bank.account.dto.BalanceDto;
import pl.mbcode.nn.bank.account.dto.CreateAccountCommandDto;
import pl.mbcode.nn.bank.command.CreateAccountCommand;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface AccountMapper {

    static AccountMapper get() {
        return Mappers.getMapper(AccountMapper.class);
    }


    @Mapping(source = "initialBalanceInPLN", target = "initialBalanceInPLN", qualifiedByName = "doubleToBigDecimal")
    CreateAccountCommand toCommand(CreateAccountCommandDto dto);

    @Mapping(source = "balances", target = "balances", qualifiedByName = "balanceMapToList")
    AccountDto toDto(Account account);

    @Named("doubleToBigDecimal")
    static BigDecimal doubleToBigDecimal(double value) {
        return BigDecimal.valueOf(value);
    }

    @Named("balanceMapToList")
    static List<BalanceDto> balanceMapToList(Map<Currency, BigDecimal> inputMap) {
        return inputMap.entrySet().stream()
                .map(entry -> new BalanceDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
