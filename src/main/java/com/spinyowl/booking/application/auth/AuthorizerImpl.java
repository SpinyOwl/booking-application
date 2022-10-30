package com.spinyowl.booking.application.auth;

import com.google.inject.Inject;
import io.muserver.rest.Authorizer;
import java.security.Principal;
import lombok.NoArgsConstructor;

@NoArgsConstructor(onConstructor_ = @Inject)
public class AuthorizerImpl implements Authorizer {

  @Override
  public boolean isInRole(Principal principal, String role) {
    return principal instanceof BookingPrincipal bookingPrincipal
        && bookingPrincipal.getUser().getRoles().stream()
            .anyMatch(r -> role.equals(r.getRoleName()));
  }
}
