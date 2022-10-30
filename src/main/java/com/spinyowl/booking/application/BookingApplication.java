package com.spinyowl.booking.application;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.spinyowl.booking.application.auth.AuthModule;
import com.spinyowl.booking.application.resource.ResourceModule;
import com.spinyowl.booking.application.service.ServiceModule;
import com.spinyowl.booking.application.storage.StorageModule;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class BookingApplication {

  public static void main(String[] args) {
    Injector injector =
        Guice.createInjector(
            new ServiceModule(), new StorageModule(), new ResourceModule(), new AuthModule());
    injector.getInstance(BookingServer.class).start();
  }
}
