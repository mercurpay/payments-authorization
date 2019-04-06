package tech.claudioed.authorization.domain.resources.data;

import lombok.Data;

/**
 * @author claudioed on 2019-04-06.
 * Project payment-authorization
 */
@Data
public class RequestCheckAuthCode {

  private String id;

  private String userId;

}
