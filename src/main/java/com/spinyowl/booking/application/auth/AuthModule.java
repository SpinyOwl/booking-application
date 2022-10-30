package com.spinyowl.booking.application.auth;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.muserver.rest.Authorizer;
import io.muserver.rest.BasicAuthSecurityFilter;
import io.muserver.rest.UserPassAuthenticator;
import javax.ws.rs.container.ContainerRequestFilter;

public class AuthModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(Authorizer.class).to(AuthorizerImpl.class);
    bind(UserPassAuthenticator.class).to(UserPassAuthenticatorImpl.class);
  }

  @Provides
  ContainerRequestFilter authFilter(UserPassAuthenticator authenticator, Authorizer authorizer) {
    return new BasicAuthSecurityFilter("BookingApplication", authenticator, authorizer);
  }
}
