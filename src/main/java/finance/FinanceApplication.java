package finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinanceApplication {

  public static void main(String[] args) {
    // Запуск Spring Boot додатка
    SpringApplication.run(FinanceApplication.class, args);
    System.out.println("=== Finance System Started Successfully ===");
  }
}
