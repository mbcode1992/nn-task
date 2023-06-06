package pl.mbcode.nn.bank.account;

import java.util.UUID;

interface AccountRepository {

    Account save(Account account);

    Account getById(UUID id);
}
