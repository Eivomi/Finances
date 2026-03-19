package finance.service;

import finance.model.Transaction;
import finance.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import finance.dto.TransactionReportDTO;
import java.time.LocalDateTime;


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
    // 1. Перевірка через Fraud Service
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

    // 4. Зберігаємо фінальний стан у репозиторій
    return transactionRepository.save(request);
  }


  public TransactionReportDTO generateReport() {
    var allTransactions = transactionRepository.findAll();

    double totalVolume = allTransactions.stream()
      .filter(t -> "COMPLETED".equals(t.getStatus()))
      .mapToDouble(Transaction::getAmount)
      .sum();

    // Створюємо об'єкт DTO замість HashMap
    return new TransactionReportDTO(
      (long) allTransactions.size(),
      totalVolume,
      "UAH",
      LocalDateTime.now()
    );
  }
}
