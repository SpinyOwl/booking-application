package com.spinyowl.booking.application.storage;

import com.spinyowl.booking.application.model.Booking;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingStorage {

  /** Create booking. */
  Booking create(Booking booking);

  /** Delete booking. */
  void delete(long id);

  /** Find booking by id. */
  Booking findById(long id);

  /** Find all bookings for specific customer. */
  List<Booking> findByCustomerId(long customerId);

  /** Find all bookings for specific customer and date. */
  List<Booking> findByCustomerIdAndDate(long customerId, LocalDate date);

  /** Find all bookings for specific date. */
  List<Booking> findByDate(LocalDate date);

  /** Find all bookings. */
  List<Booking> findAll();

  Set<Booking> findIntersectingBookings(Booking booking);
}
