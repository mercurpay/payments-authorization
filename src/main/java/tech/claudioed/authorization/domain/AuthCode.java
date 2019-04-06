package tech.claudioed.authorization.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

/** @author claudioed on 2019-04-06. Project payment-authorization */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthCode {

  private String id;

  private String userId;

  private LocalDateTime createdAt;

  private LocalDateTime validUntil;

  private BigDecimal value;

  public Document toDoc() {
    return new Document()
        .append("_id", this.id)
        .append("userId", this.userId)
        .append("createdAt", this.createdAt)
        .append("validUntil", this.validUntil)
        .append("value", this.validUntil);
  }
}
