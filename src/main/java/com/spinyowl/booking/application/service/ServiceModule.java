package com.spinyowl.booking.application.service;

import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(UserService.class).to(UserServiceImpl.class);
    bind(TableService.class).to(TableServiceImpl.class);
    bind(BookingService.class).to(BookingServiceImpl.class);
  }
}
