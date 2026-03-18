package finance.service;

import org.springframework.stereotype.Service;
//Реалізує бізнес-логіку перевірки транзакцій на підозрілу активність.
@Service
public class FraudService {

  private static final double FRAUD_LIMIT = 50000.0;

  public boolean isSuspicious(Double amount) {
    if (amount == null) return false;

    // Якщо сума перевищує ліміт, система ідентифікує її як підозрілу
    return amount > FRAUD_LIMIT;
  }
}
