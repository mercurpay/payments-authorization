package tech.claudioed.authorization.domain;

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
public class CheckedAuthCode {

  private String id;

  private LocalDateTime usedAt;

  private AuthCode data;

  public Document toDoc() {
    return new Document()
        .append("_id", this.id)
        .append("usedAt", this.usedAt)
        .append("data", this.data);
  }
}
