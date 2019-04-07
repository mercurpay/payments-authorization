package tech.claudioed.authorization.domain.exception;

import tech.claudioed.authorization.domain.AuthCode;

/**
 * @author claudioed on 2019-04-07.
 * Project payment-authorization
 */
public class AuthCodeExpired extends RuntimeException{

  public AuthCodeExpired(AuthCode authCode) {
    super("AuthCode expired");
  }

}
