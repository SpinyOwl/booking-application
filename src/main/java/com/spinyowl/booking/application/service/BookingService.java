package com.spinyowl.booking.application.service;

import com.spinyowl.booking.application.model.Booking;
import com.spinyowl.booking.application.model.User;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.NonNull;

/** Booking service. Allows to manage bookings. */
public interface BookingService {

  /**
   * As a customer I want to be able to book a table in a restaurant.
   *
   * @param booking booking to be created.
   * @return created booking.
   */
  Booking createBooking(@NonNull Booking booking);

  /**
   * As a customer I want to be able to cancel my booking.
   *
   * @param bookingId id of the booking to be canceled.
   * @return canceled booking.
   */
  Booking cancelBooking(long bookingId);

  /**
   * As a customer I want to be able to see my booking.
   *
   * @param bookingId id of the booking to be shown.
   * @param customer booking customer.
   * @return shown booking.
   */
  Booking showBooking(long bookingId, @NonNull User customer);

  /**
   * As a customer I want to be able to see all my bookings.
   *
   * @param userId id of booking customer.
   * @return list of all customer bookings.
   */
  List<Booking> showAllBookings(long userId);
  /**
   * As a customer I want to be able to see all my bookings.
   *
   * @param userId id of booking customer.
   * @return list of all customer bookings.
   */
  List<Booking> showAllBookings(long userId, LocalDate date);
  /**
   * As a customer I want to be able to see all my bookings.
   *
   * @return list of all customer bookings.
   */
  List<Booking> showAllBookings();
  /**
   * As a customer I want to be able to see all my bookings.
   *
   * @return list of all customer bookings.
   */
  List<Booking> showAllBookings(LocalDate date);
}
