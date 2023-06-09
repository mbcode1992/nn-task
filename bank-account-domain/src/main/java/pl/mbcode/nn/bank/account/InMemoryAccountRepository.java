package pl.mbcode.nn.bank.account;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

class InMemoryAccountRepository implements AccountRepository {

    private static final Map<UUID, Account> accountMap = new HashMap<>();

    @Override
    public Account save(Account account) {
        accountMap.put(account.getId(), account);
        return account;
    }

    @Override
    public Account getById(UUID id) {
        return Optional.ofNullable(accountMap.get(id))
                .orElseThrow(() -> AccountNotFoundException.forId(id));
    }

    void clear(){
        accountMap.clear();
    }
}
