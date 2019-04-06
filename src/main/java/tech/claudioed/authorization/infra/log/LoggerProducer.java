package tech.claudioed.authorization.infra.log;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author claudioed on 2019-04-06. Project payment-authorization */
@ApplicationScoped
public class LoggerProducer {

  @Produces
  public Logger produceLogger(InjectionPoint injectionPoint) {
    return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
  }

}
