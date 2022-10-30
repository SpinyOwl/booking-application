package com.spinyowl.booking.application.resource;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class ResourceModule extends AbstractModule {

  @Override
  protected void configure() {
    //    bind(UserResource.class);
    //    bind(TableResource.class);
    bind(BookingResource.class);
    Multibinder<Resource> resourceBinder = Multibinder.newSetBinder(binder(), Resource.class);
    resourceBinder.addBinding().to(BookingResource.class);
  }
}
