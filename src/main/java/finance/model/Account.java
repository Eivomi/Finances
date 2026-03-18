package finance.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
  private Long id;

  @NotBlank(message = "Ім'я власника обов'язкове")
  private String owner;

  @PositiveOrZero(message = "Баланс не може бути від'ємним")
  private Double balance;

  @NotBlank(message = "Валюта обов'язкова")
  @Size(min = 3, max = 3, message = "Код валюти має складатися з 3 символів (напр. UAH)")
  private String currency;
}
