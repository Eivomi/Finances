package finance.repository;

import finance.model.Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TransactionRepository {

  // Список для зберігання транзакцій у пам'яті (In-memory)
  private final List<Transaction> transactions = new ArrayList<>();

  // Генератор унікальних ідентифікаторів для транзакцій
  private final AtomicLong idGenerator = new AtomicLong(1);


   // Зберігає нову транзакцію, присвоює їй ID та мітку часу.
  public Transaction save(Transaction transaction) {
    if (transaction.getId() == null) {
      transaction.setId(idGenerator.getAndIncrement());
    }
    if (transaction.getTimestamp() == null) {
      transaction.setTimestamp(LocalDateTime.now());
    }
    transactions.add(transaction);
    return transaction;
  }

  // Повертає копію списку всіх транзакцій.
  public List<Transaction> findAll() {
    return new ArrayList<>(transactions);
  }

  public void clear() {
    transactions.clear();
    idGenerator.set(1);
  }
}
