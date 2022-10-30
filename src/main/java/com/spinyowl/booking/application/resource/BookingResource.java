package com.spinyowl.booking.application.resource;

import com.spinyowl.booking.application.auth.BookingPrincipal;
import com.spinyowl.booking.application.model.Booking;
import com.spinyowl.booking.application.model.Role;
import com.spinyowl.booking.application.model.User;
import com.spinyowl.booking.application.service.BookingService;
import com.spinyowl.booking.application.service.UserService;
import io.muserver.rest.Description;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Path("/booking")
@Description("Booking management endpoint")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BookingResource implements Resource {

  @NonNull private final BookingService bookingService;
  @NonNull private final UserService userService;

  @GET
  @Description("Returns all bookings")
  @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
  public List<Booking> getAll(
      @Context SecurityContext securityContext, @QueryParam("date") String date) {
    if (securityContext.isUserInRole(Role.OWNER.getRoleName())) {
      return bookingService.showAllBookings(parseDate(date));
    }
    return bookingService.showAllBookings(getUser(securityContext).getId(), parseDate(date));
  }

  @POST
  @Description("Create new booking")
  @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
  public Booking create(@Context SecurityContext securityContext, Booking booking) {
    if (!securityContext.isUserInRole(Role.OWNER.getRoleName())) {
      User user = getUser(securityContext);
      if (!user.equals(booking.getCustomer())) {
        throw new ForbiddenException("You can't create booking for another user.");
      }
      return bookingService.createBooking(booking);
    }
    return bookingService.createBooking(booking);
  }

  private User getUser(SecurityContext securityContext) {
    if (securityContext.getUserPrincipal() instanceof BookingPrincipal principal) {
      return principal.getUser();
    }
    throw new NotAuthorizedException(Status.UNAUTHORIZED);
  }

  private LocalDate parseDate(String date) {
    return date == null || date.isEmpty()
        ? null
        : LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
  }
}
