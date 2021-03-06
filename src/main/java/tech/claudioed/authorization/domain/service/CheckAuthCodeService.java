package tech.claudioed.authorization.domain.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import tech.claudioed.authorization.domain.AuthCode;
import tech.claudioed.authorization.domain.CheckedAuthCode;
import tech.claudioed.authorization.domain.repository.AuthCodeRepository;
import tech.claudioed.authorization.domain.resources.data.RequestCheckAuthCode;

/** @author claudioed on 2019-04-06. Project payment-authorization */
@ApplicationScoped
public class CheckAuthCodeService {

  @Inject
  AuthCodeRepository authCodeRepository;

  @Inject
  Logger logger;

  public CheckedAuthCode check(String id,RequestCheckAuthCode requestCheckAuthCode) {
    this.logger.info("Checking auth code for userId {} ...", requestCheckAuthCode.getUserId());
    final AuthCode authCode =  this.authCodeRepository.find(
            id, requestCheckAuthCode.getUserId());
    logger.info("AuthCode is valid until {}",authCode.getValidUntil().toString());
    if(authCode.isExpired()){
      logger.error("AuthCode is {} expired",authCode.getId());
    }
    logger.info("AuthCode value {}",authCode.getValue());
    if(!authCode.isSameValue(requestCheckAuthCode.getValue())){
      logger.error("AuthCode id is {} has different value",authCode.getId());
    }
    return this.authCodeRepository.registerChecked(authCode);
  }
}
