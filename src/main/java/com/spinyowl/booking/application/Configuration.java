package com.spinyowl.booking.application;

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
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class Configuration {

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Configuration.class);

  private final UserStorage userStorage;
  private final TableStorage tableStorage;
  private final BookingStorage bookingStorage;

  public void init() {
    logger.info("Initializing application...");

    User admin =
        userStorage.create(User.builder().username("admin").role(Role.OWNER).active(true).build());
    User customer1 =
        userStorage.create(
            User.builder().username("customer1").role(Role.CUSTOMER).active(true).build());

    Table table1 =
        tableStorage.create(
            Table.builder().seats(4).description("4 seats table near the window.").build());
    Table table2 =
        tableStorage.create(
            Table.builder().seats(2).description("2 seats table near the window.").build());

    bookingStorage.create(
        Booking.builder()
            .customer(customer1)
            .table(table1)
            .startDate(LocalDate.now())
            .startTime(LocalTime.now().withSecond(0).withMinute(0))
            .duration(Duration.ofHours(2))
            .build());
    bookingStorage.create(
        Booking.builder()
            .customer(customer1)
            .table(table1)
            .table(table2)
            .startDate(LocalDate.now().plusDays(1))
            .startTime(LocalTime.now().withSecond(0).withMinute(0))
            .duration(Duration.ofHours(4))
            .build());

    logger.info("Application initialized.");
  }
}
