package com.spinyowl.booking.application.resource;

import java.security.Principal;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/auth")
@Produces(MediaType.TEXT_PLAIN)
public class AuthResource {

  @GET
  public String status(@Context SecurityContext securityContext) {
    Principal userPrincipal = securityContext.getUserPrincipal();
    return userPrincipal == null ? "Unathorized" : "Authorized as " + userPrincipal.getName();
  }
}
