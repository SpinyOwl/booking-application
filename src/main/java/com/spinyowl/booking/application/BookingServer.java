package com.spinyowl.booking.application;

import static io.muserver.Mutils.htmlEncode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.inject.Inject;
import com.spinyowl.booking.application.model.Role;
import com.spinyowl.booking.application.model.User;
import com.spinyowl.booking.application.resource.Resource;
import com.spinyowl.booking.application.storage.UserStorage;
import io.muserver.Method;
import io.muserver.MuServerBuilder;
import io.muserver.rest.RestHandlerBuilder;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.container.ContainerRequestFilter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BookingServer {

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(BookingServer.class);
  public static final int PORT = 8080;

  @NonNull private Set<Resource> resources;
  @NonNull private Configuration configuration;
  @NonNull private ContainerRequestFilter authFilter;

  @NonNull private UserStorage userStorage;

  public void start() {
    configuration.init();

    logger.info("Starting Booking Application...");
    JacksonJaxbJsonProvider jsonProvider = getJacksonJaxbJsonProvider();
    MuServerBuilder.httpServer()
        .addHandler(
            Method.GET,
            "/stop",
            (req, res, params) -> {
              res.write("Stopping server...");
              logger.info("Stopping server...");
              req.server().stop();
            })
        .addHandler(
            RestHandlerBuilder.restHandler(resources.toArray())
                .addRequestFilter(authFilter)
                .addCustomWriter(jsonProvider)
                .addCustomReader(jsonProvider))
        .addHandler(
            Method.GET,
            "/",
            (request, response, pathParams) -> {
              response.contentType("text/html");
              response.write(getDemoPageHtml());
            })
        .withHttpPort(PORT)
        .start();

    logger.info("Booking Application started on port {}.", PORT);
  }

  private String getDemoPageHtml() {
    StringBuilder html = new StringBuilder();
    html.append(
        """
<html>
<head><title>Basic Auth Demo</title></head>
<style>
  table { border: 1px solid lightgray; border-collapse: collapse; }
  th, td { border: 1px solid lightgray; padding: 5px 10px; text-align: start; }
</style>
<body>
<h1>Users</h1>
<table>
  <thead>
  <tr>
    <th>username</th>
    <th>password</th>
    <th>roles</th>
  </tr>
  </thead>
  <tbody>
""");
    for (User user : userStorage.findAll()) {
      html.append("<tr><td>").append(htmlEncode(user.getUsername())).append("</td>");
      html.append("<td></td><td>")
          .append(
              htmlEncode(
                  user.getRoles().stream()
                      .map(Role::getRoleName)
                      .collect(Collectors.joining(", "))))
          .append("</td></tr>");
    }
    html.append("</tbody></table>");
    html.append("</body></html>");
    html.append("<p></p>");
    html.append("<a href=\"/booking\">Read all bookings (requires User/Admin role)</a>");
    return html.toString();
  }

  private static JacksonJaxbJsonProvider getJacksonJaxbJsonProvider() {
    return new JacksonJaxbJsonProvider(
        new ObjectMapper().findAndRegisterModules(), JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
  }
}
