package finance.controller;

import finance.model.Transaction;
import finance.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

  private final TransactionService transactionService;

  // Впровадження залежності через конструктор (Constructor Injection)
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping
  public ResponseEntity<Transaction> execute(@RequestBody Transaction request) {
    Transaction result = transactionService.process(request);

    HttpStatus status = "COMPLETED".equals(result.getStatus()) ? HttpStatus.CREATED :
      (result.getStatus().contains("BLOCKED") ? HttpStatus.FORBIDDEN : HttpStatus.BAD_REQUEST);

    return new ResponseEntity<>(result, status);
  }

  @GetMapping("/report")
  public Map<String, Object> getReport() {
    return transactionService.generateReport();
  }
}
