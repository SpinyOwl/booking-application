package com.spinyowl.booking.application.storage;

import com.google.inject.AbstractModule;
import com.spinyowl.booking.application.storage.hashmap.BookingStorageImpl;
import com.spinyowl.booking.application.storage.hashmap.TableStorageImpl;
import com.spinyowl.booking.application.storage.hashmap.UserStorageImpl;

public class StorageModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(UserStorage.class).to(UserStorageImpl.class).asEagerSingleton();
    bind(TableStorage.class).to(TableStorageImpl.class).asEagerSingleton();
    bind(BookingStorage.class).to(BookingStorageImpl.class).asEagerSingleton();
  }
}
