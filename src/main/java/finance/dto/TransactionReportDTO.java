package finance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionReportDTO {
  private long totalTransactions;
  private double totalVolume;
  private String currency;
  private LocalDateTime reportDate;
}
