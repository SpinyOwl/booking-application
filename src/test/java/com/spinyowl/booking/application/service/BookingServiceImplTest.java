package com.spinyowl.booking.application.service;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.inject.Guice;
import com.spinyowl.booking.application.model.Booking;
import com.spinyowl.booking.application.model.Role;
import com.spinyowl.booking.application.model.Table;
import com.spinyowl.booking.application.model.User;
import com.spinyowl.booking.application.storage.BookingStorage;
import com.spinyowl.booking.application.storage.TableStorage;
import com.spinyowl.booking.application.storage.UserStorage;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

  public static final User ADMIN = new User(0L, "admin", List.of(Role.OWNER), true);
  public static final Table TABLE = new Table(0L, 4, "4 seats table near the window.");
  public static final Booking BOOKING = new Booking(0L, ADMIN, List.of(TABLE),
      LocalDate.of(2022, 1, 1), LocalTime.of(10, 0, 0), Duration.ofHours(2),
      "Please add flowers :)");

  @Mock
  BookingStorage bookingStorage;
  @Mock
  UserStorage userStorage;
  @Mock
  TableStorage tableStorage;

  @Inject
  private BookingService bookingService;

  @BeforeEach
  public void setUp() {
    // create guice injector for test
    Guice.createInjector(binder -> {
      binder.bind(BookingStorage.class).toProvider(() -> bookingStorage);
      binder.bind(UserStorage.class).toProvider(() -> userStorage);
      binder.bind(TableStorage.class).toProvider(() -> tableStorage);
      binder.bind(BookingService.class).to(BookingServiceImpl.class);
    }).injectMembers(this);
  }

  @Test
  void createBooking() {
    // Arrange
    when(userStorage.findById(ADMIN.getId())).thenReturn(ADMIN);
    when(tableStorage.findById(TABLE.getId())).thenReturn(TABLE);
    when(bookingStorage.create(any())).thenReturn(BOOKING);

    // Act
    Booking booking = Booking.builder().customer(User.builder().id(ADMIN.getId()).build())
        .table(Table.builder().id(TABLE.getId()).build()).startDate(BOOKING.getStartDate())
        .startTime(BOOKING.getStartTime()).duration(BOOKING.getDuration()).notes(BOOKING.getNotes())
        .build();
    Booking createdBooking = bookingService.createBooking(booking);

    // Assert

    ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
    verify(bookingStorage).create(bookingCaptor.capture());
    Booking expected = booking.toBuilder().clearTables().table(TABLE).customer(ADMIN).build();
    assertThat(bookingCaptor.getValue()).isEqualTo(expected);

    assertThat(createdBooking).isEqualTo(BOOKING);

  }

  @Test
  void showAllBookings_succeeds() {
    // Arrange
    when(bookingStorage.findAll()).thenReturn(List.of(BOOKING));

    // Act
    List<Booking> bookings = bookingService.showAllBookings();

    // Assert
    assertThat(bookings).containsExactly(BOOKING);
    verify(bookingStorage).findAll();
  }

  @Test
  void showAllBookings_withDate_succeeds() {
    // Arrange
    LocalDate date = LocalDate.now();
    when(bookingStorage.findByDate(date)).thenReturn(List.of(BOOKING));

    // Act
    List<Booking> bookings = bookingService.showAllBookings(date);

    // Assert
    assertThat(bookings).containsExactly(BOOKING);
    verify(bookingStorage).findByDate(date);
    verify(bookingStorage, never()).findAll();

  }

  @Test
  void showAllBookings_withNullDate_succeeds() {
    // Arrange
    LocalDate date = null;
    when(bookingStorage.findAll()).thenReturn(List.of(BOOKING));

    // Act
    List<Booking> bookings = bookingService.showAllBookings(date);

    // Assert
    assertThat(bookings).containsExactly(BOOKING);
    verify(bookingStorage, never()).findByDate(date);
    verify(bookingStorage).findAll();
  }

  @Test
  void testShowAllBookings1() {
  }

  @Test
  void testShowAllBookings2() {
  }
}
