package pl.mbcode.nn.bank.account;

import java.util.UUID;

interface AccountRepository {

    Account save(Account cart);

    Account getById(UUID id);
}
