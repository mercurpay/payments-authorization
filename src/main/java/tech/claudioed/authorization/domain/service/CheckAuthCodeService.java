package tech.claudioed.authorization.domain.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import tech.claudioed.authorization.domain.AuthCode;
import tech.claudioed.authorization.domain.CheckedAuthCode;
import tech.claudioed.authorization.domain.repository.AuthCodeRepository;
import tech.claudioed.authorization.domain.resources.data.RequestCheckAuthCode;

/**
 * @author claudioed on 2019-04-06.
 * Project payment-authorization
 */
@ApplicationScoped
public class CheckAuthCodeService {

  private final AuthCodeRepository authCodeRepository;

  @Inject
  public CheckAuthCodeService(
      AuthCodeRepository authCodeRepository) {
    this.authCodeRepository = authCodeRepository;
  }

  public CheckedAuthCode check(RequestCheckAuthCode requestCheckAuthCode){
    final AuthCode authCode = this.authCodeRepository
        .find(requestCheckAuthCode.getId(), requestCheckAuthCode.getUserId());
    return this.authCodeRepository.registerChecked(authCode);
  }

}
