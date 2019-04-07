package tech.claudioed.authorization.domain.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import tech.claudioed.authorization.domain.CheckedAuthCode;
import tech.claudioed.authorization.domain.resources.data.AuthCodeData;
import tech.claudioed.authorization.domain.resources.data.RequestCheckAuthCode;
import tech.claudioed.authorization.domain.resources.data.RequestNewAuthCode;
import tech.claudioed.authorization.domain.service.CheckAuthCodeService;
import tech.claudioed.authorization.domain.service.RequestNewAuthCodeService;

@Path("/api/authorizations")
public class AuthorizationResources {

  @Inject
  private CheckAuthCodeService checkAuthCodeService;

  @Inject
  private RequestNewAuthCodeService requestNewAuthCodeService;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response requestNewAuthCode(RequestNewAuthCode requestNewAuthCode, @Context UriInfo uriInfo) {
    final AuthCodeData authCode = this.requestNewAuthCodeService.requestNew(requestNewAuthCode);
    UriBuilder builder = uriInfo.getAbsolutePathBuilder();
    builder.path(authCode.getId());
    return Response.ok(builder.build()).entity(authCode).build();
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response checkAuthCode(RequestCheckAuthCode requestCheckAuthCode) {
    final CheckedAuthCode checkedAuthCode = this.checkAuthCodeService.check(requestCheckAuthCode);
    return Response.ok(checkedAuthCode).build();
  }

}
