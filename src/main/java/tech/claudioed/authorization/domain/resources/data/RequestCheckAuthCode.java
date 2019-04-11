package tech.claudioed.authorization.domain.resources.data;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @author claudioed on 2019-04-06.
 * Project payment-authorization
 */
@Data
public class RequestCheckAuthCode {

  private String userId;

  private BigDecimal value;

}
