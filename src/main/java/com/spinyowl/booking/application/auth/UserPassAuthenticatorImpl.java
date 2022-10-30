package com.spinyowl.booking.application.auth;

import com.google.inject.Inject;
import com.spinyowl.booking.application.model.User;
import com.spinyowl.booking.application.storage.UserStorage;
import io.muserver.rest.UserPassAuthenticator;
import java.security.Principal;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class UserPassAuthenticatorImpl implements UserPassAuthenticator {

  @NonNull private final UserStorage userStorage;

  @Override
  public Principal authenticate(String username, String password) {
    User user = userStorage.findByUsername(username);
    return user == null ? null : new BookingPrincipal(user);
  }
}
