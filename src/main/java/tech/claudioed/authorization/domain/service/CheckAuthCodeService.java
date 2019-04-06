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
  private AuthCodeRepository authCodeRepository;

  @Inject
  private Logger logger;

  public CheckedAuthCode check(RequestCheckAuthCode requestCheckAuthCode) {
    this.logger.info("Checking auth code for userId {}", requestCheckAuthCode.getUserId());
    final AuthCode authCode =
        this.authCodeRepository.find(
            requestCheckAuthCode.getId(), requestCheckAuthCode.getUserId());

    return this.authCodeRepository.registerChecked(authCode);
  }
}
