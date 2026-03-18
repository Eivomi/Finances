package finance.repository;

import finance.model.Account;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AccountRepository {
  private final Map<Long, Account> accounts = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(1);

  public Account save(Account account) {
    if (account.getId() == null) {
      account.setId(idGenerator.getAndIncrement());
    }
    accounts.put(account.getId(), account);
    return account;
  }

  public List<Account> findAll() {
    return new ArrayList<>(accounts.values());
  }

  public Optional<Account> findById(Long id) {
    return Optional.ofNullable(accounts.get(id));
  }
}
