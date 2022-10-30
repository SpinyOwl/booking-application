package com.spinyowl.booking.application.storage.hashmap;

import com.spinyowl.booking.application.model.Booking;
import com.spinyowl.booking.application.model.Table;
import com.spinyowl.booking.application.storage.BookingStorage;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class BookingStorageImpl implements BookingStorage {

  private final AtomicLong counter = new AtomicLong(0);
  private final Map<Long, Booking> bookingMap = new HashMap<>();

  @Override
  public Booking create(Booking booking) {
    booking.setId(nextId());
    bookingMap.put(booking.getId(), booking);
    return booking;
  }

  @Override
  public void delete(long id) {
    bookingMap.remove(id);
  }

  @Override
  public Booking findById(long id) {
    return bookingMap.get(id);
  }

  @Override
  public List<Booking> findByCustomerId(long customerId) {
    return bookingMap.values().stream()
        .filter(booking -> belongsToCustomer(booking, customerId))
        .toList();
  }

  @Override
  public List<Booking> findByCustomerIdAndDate(long customerId, LocalDate date) {
    return bookingMap.values().stream()
        .filter(booking -> belongsToCustomer(booking, customerId) && atDate(booking, date))
        .toList();
  }

  @Override
  public List<Booking> findByDate(LocalDate date) {
    return bookingMap.values().stream().filter(booking -> atDate(booking, date)).toList();
  }

  private static boolean atDate(Booking booking, LocalDate date) {
    return booking.getStartDate().equals(date)
        || booking
            .getStartTime()
            .atDate(booking.getStartDate())
            .plus(booking.getDuration())
            .toLocalDate()
            .equals(date);
  }

  @Override
  public List<Booking> findAll() {
    return bookingMap.values().stream().toList();
  }

  public Set<Booking> findIntersectingBookings(Booking booking) {
    Set<Booking> intersections = new HashSet<>();
    for (Booking existingBooking : bookingMap.values()) {
      for (Table existingBookingTable : existingBooking.getTables()) {
        for (Table newBookingTable : booking.getTables()) {
          if (existingBookingTable.getId().equals(newBookingTable.getId())
              && (isBefore(booking, existingBooking) || isAfter(booking, existingBooking))) {
            intersections.add(existingBooking);
          }
        }
      }
    }
    return intersections;
  }

  private static boolean isAfter(Booking booking, Booking existingBooking) {
    return existingBooking
        .getStartTime()
        .atDate(existingBooking.getStartDate())
        .plus(existingBooking.getDuration())
        .isAfter(booking.getStartTime().atDate(booking.getStartDate()));
  }

  private static boolean isBefore(Booking booking, Booking existingBooking) {
    return existingBooking
        .getStartTime()
        .atDate(existingBooking.getStartDate())
        .isBefore(
            booking.getStartTime().atDate(booking.getStartDate()).plus(booking.getDuration()));
  }

  private static boolean belongsToCustomer(Booking booking, long customerId) {
    return booking.getCustomer().getId().equals(customerId);
  }

  private long nextId() {
    return counter.getAndIncrement();
  }
}
