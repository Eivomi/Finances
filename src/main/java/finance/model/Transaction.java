package finance.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
  private Long id;

  @NotNull(message = "ID відправника обов'язковий")
  private Long fromAccountId;

  @NotNull(message = "ID отримувача обов'язковий")
  private Long toAccountId;

  @Positive(message = "Сума переказу має бути більшою за нуль")
  private Double amount;

  private LocalDateTime timestamp;
  private String status;
}
