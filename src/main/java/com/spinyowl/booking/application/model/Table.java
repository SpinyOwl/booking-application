package com.spinyowl.booking.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** Class represents single table in the restaurant. */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Table {
  private Long id;
  private Integer seats;
  private String description;
}
