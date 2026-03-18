package finance.controller;

import finance.model.Account;
import finance.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping
  public List<Account> getAll() {
    return accountService.getAll();
  }

  @PostMapping
  public ResponseEntity<Account> create(@Valid @RequestBody Account account) {
    return new ResponseEntity<>(accountService.create(account), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Account> getById(@PathVariable Long id) {
    return accountService.getById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }
}
