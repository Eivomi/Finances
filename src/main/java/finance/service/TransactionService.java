package finance.service;

import finance.model.Transaction;
import finance.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final AccountService accountService;
  private final FraudService fraudService;

  // Конструктор для впровадження всіх необхідних залежностей
  public TransactionService(TransactionRepository transactionRepository,
                            AccountService accountService,
                            FraudService fraudService) {
    this.transactionRepository = transactionRepository;
    this.accountService = accountService;
    this.fraudService = fraudService;
  }

  /**
   * Основна бізнес-логіка транзакції
   */
  public Transaction process(Transaction request) {
    // 1. Перевірка через Fraud Service [cite: 495]
    if (fraudService.isSuspicious(request.getAmount())) {
      request.setStatus("BLOCKED_BY_FRAUD_SYSTEM");
      return transactionRepository.save(request);
    }

    // 2. Викликаємо AccountService для зміни балансів
    String result = accountService.transfer(
      request.getFromAccountId(),
      request.getToAccountId(),
      request.getAmount()
    );

    // 3. Встановлюємо статус на основі результату переказу
    if ("SUCCESS".equals(result)) {
      request.setStatus("COMPLETED");
    } else {
      request.setStatus("FAILED: " + result);
    }

    // 4. Зберігаємо фінальний стан у репозиторій [cite: 510]
    return transactionRepository.save(request);
  }


   // Генерація звіту (DTO)
  public Map<String, Object> generateReport() {
    var allTransactions = transactionRepository.findAll();

    double totalVolume = allTransactions.stream()
      .filter(t -> "COMPLETED".equals(t.getStatus()))
      .mapToDouble(Transaction::getAmount)
      .sum();

    Map<String, Object> report = new HashMap<>();
    report.put("totalTransactions", allTransactions.size());
    report.put("totalVolume", totalVolume);
    report.put("currency", "UAH");
    report.put("reportDate", LocalDateTime.now());

    return report;
  }
}
