package com.spinyowl.booking.application.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

/** Base class for all users. */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private Long id;
  private String username;
  @Singular private List<Role> roles;
  private boolean active;

  public boolean isCustomer() {
    return this.getRoles().contains(Role.CUSTOMER);
  }

  public boolean isOwner() {
    return this.getRoles().contains(Role.OWNER);
  }
}
