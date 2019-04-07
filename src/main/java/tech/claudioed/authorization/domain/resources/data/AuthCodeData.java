package tech.claudioed.authorization.domain.resources.data;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author claudioed on 2019-04-06. Project payment-authorization */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthCodeData {

  private String id;

  private String userId;

  private BigDecimal value;
}
