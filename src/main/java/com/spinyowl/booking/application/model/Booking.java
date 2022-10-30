package com.spinyowl.booking.application.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

/** Class represents single booking. */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public final class Booking {

  /** Booking id. */
  private Long id;
  /** Customer for which was booked the table. */
  private User customer;
  /** Table which was booked. */
  @Singular private List<Table> tables;
  /** Booking start date. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;
  /** Booking start time. */
  @JsonFormat(pattern = "HH:mm:ss")
  private LocalTime startTime;
  /** Duration for how long booking is active. */
  @JsonFormat(shape = Shape.STRING)
  private Duration duration;
  /** Notes for the booking. */
  private String notes;
}
