package finance.service;

import finance.model.Account;
import finance.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
  private final AccountRepository repository;

  public AccountService(AccountRepository repository) {
    this.repository = repository;
  }

  public List<Account> getAll() {
    return repository.findAll();
  }

  public Account create(Account account) {
    return repository.save(account);
  }

  public Optional<Account> getById(Long id) {
    return repository.findById(id);
  }

  public String transfer(Long fromId, Long toId, Double amount) {
    Optional<Account> fromOpt = repository.findById(fromId);
    Optional<Account> toOpt = repository.findById(toId);

    if (fromOpt.isEmpty() || toOpt.isEmpty()) {
      return "ACCOUNT_NOT_FOUND";
    }

    Account from = fromOpt.get();
    Account to = toOpt.get();

    if (from.getBalance() < amount) {
      return "INSUFFICIENT_FUNDS";
    }

    // Логіка зміни балансів
    from.setBalance(from.getBalance() - amount);
    to.setBalance(to.getBalance() + amount);

    // Збереження оновлених даних у репозиторій
    repository.save(from);
    repository.save(to);

    return "SUCCESS";
  }
}
