package tech.claudioed.authorization.domain.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import tech.claudioed.authorization.domain.AuthCode;
import tech.claudioed.authorization.domain.repository.AuthCodeRepository;
import tech.claudioed.authorization.domain.resources.data.AuthCodeData;
import tech.claudioed.authorization.domain.resources.data.RequestNewAuthCode;

/** @author claudioed on 2019-04-06. Project payment-authorization */
@ApplicationScoped
public class RequestNewAuthCodeService {

  @Inject
  private AuthCodeRepository authCodeRepository;

  @Inject
  private Logger logger;

  public AuthCodeData requestNew(RequestNewAuthCode requestNewAuthCode) {
    logger.info("UserId {} requesting new auth-code",requestNewAuthCode.getUserId());
    final AuthCode authCode = this.authCodeRepository.register(requestNewAuthCode);
    logger.info("UserId {} requested new auth code {} successfully. Auth Code {} ",requestNewAuthCode.getUserId(),authCode.getId(),authCode.toString());
    return AuthCodeData.builder().id(authCode.getId()).userId(authCode.getUserId()).value(authCode.getValue()).build();
  }
}
