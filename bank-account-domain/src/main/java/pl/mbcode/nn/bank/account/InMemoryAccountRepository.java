package pl.mbcode.nn.bank.account;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class InMemoryAccountRepository implements AccountRepository {

    private final static Map<UUID, Account> accountMap = new HashMap<>();

    @Override
    public Account save(Account account) {
        accountMap.put(account.getId(), account);
        return account;
    }
}
