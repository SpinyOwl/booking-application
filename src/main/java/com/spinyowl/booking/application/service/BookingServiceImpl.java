package com.spinyowl.booking.application.service;

import com.spinyowl.booking.application.model.Booking;
import com.spinyowl.booking.application.model.Table;
import com.spinyowl.booking.application.model.User;
import com.spinyowl.booking.application.storage.BookingStorage;
import com.spinyowl.booking.application.storage.TableStorage;
import com.spinyowl.booking.application.storage.UserStorage;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BookingServiceImpl implements BookingService {

  @NonNull private final BookingStorage bookingStorage;
  @NonNull private final UserStorage userStorage;
  @NonNull private final TableStorage tableStorage;

  @Override
  public Booking createBooking(@NonNull Booking booking) {
    User customer = userStorage.findById(booking.getCustomer().getId());
    booking.setCustomer(customer);
    for (Table table : booking.getTables()) {
      Table t = tableStorage.findById(table.getId());
      table.setSeats(t.getSeats());
      table.setDescription(t.getDescription());
    }
    return bookingStorage.create(booking);
  }

  @Override
  public Booking cancelBooking(long bookingId) {
    return null;
  }

  @Override
  public Booking showBooking(long bookingId, @NonNull User customer) {
    return null;
  }

  @Override
  public List<Booking> showAllBookings(long userId) {
    return bookingStorage.findByCustomerId(userId);
  }

  @Override
  public List<Booking> showAllBookings(long userId, LocalDate date) {
    return date == null
        ? showAllBookings(userId)
        : bookingStorage.findByCustomerIdAndDate(userId, date);
  }

  @Override
  public List<Booking> showAllBookings(LocalDate date) {
    return date == null ? showAllBookings() : bookingStorage.findByDate(date);
  }

  @Override
  public List<Booking> showAllBookings() {
    return bookingStorage.findAll();
  }
}
