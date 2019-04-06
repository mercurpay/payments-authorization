package tech.claudioed.authorization.domain.repository;

import tech.claudioed.authorization.domain.AuthCode;
import tech.claudioed.authorization.domain.CheckedAuthCode;
import tech.claudioed.authorization.domain.resources.data.RequestNewAuthCode;

/**
 * @author claudioed on 2019-04-06.
 * Project payment-authorization
 */
public interface AuthCodeRepository {

  AuthCode register(RequestNewAuthCode requestNewAuthCode);

  CheckedAuthCode registerChecked(AuthCode code);

  AuthCode find(String id,String userId);

}
