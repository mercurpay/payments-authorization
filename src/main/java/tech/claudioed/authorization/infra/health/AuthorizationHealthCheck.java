package tech.claudioed.authorization.infra.health;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

/**
 * @author claudioed on 2019-04-06.
 * Project payment-authorization
 */
@Health
@ApplicationScoped
public class AuthorizationHealthCheck implements HealthCheck {

  @Override
  public HealthCheckResponse call() {
    return HealthCheckResponse.named("Payment Authorizations is running").up().build();
  }

}
