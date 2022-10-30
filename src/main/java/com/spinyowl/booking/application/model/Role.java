package com.spinyowl.booking.application.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum Role {
  OWNER(0, "owner"),
  CUSTOMER(1, "customer");

  private final long id;
  private final String roleName;
}
