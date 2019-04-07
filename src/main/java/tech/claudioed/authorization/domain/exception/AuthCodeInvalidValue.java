package tech.claudioed.authorization.domain.exception;

import tech.claudioed.authorization.domain.AuthCode;

/**
 * @author claudioed on 2019-04-07.
 * Project payment-authorization
 */
public class AuthCodeInvalidValue extends RuntimeException{

  public AuthCodeInvalidValue(AuthCode authCode) {
    super("AuthCode has invalid value");
  }

}
