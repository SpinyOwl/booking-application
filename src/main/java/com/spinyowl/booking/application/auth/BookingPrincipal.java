package com.spinyowl.booking.application.auth;

import com.spinyowl.booking.application.model.User;
import java.security.Principal;
import lombok.Data;
import lombok.NonNull;

@Data
public class BookingPrincipal implements Principal {
  @NonNull private final User user;

  @Override
  public String getName() {
    return user.getUsername();
  }
}
