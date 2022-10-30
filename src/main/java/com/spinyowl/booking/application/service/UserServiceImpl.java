package com.spinyowl.booking.application.service;

import com.spinyowl.booking.application.model.User;
import com.spinyowl.booking.application.storage.UserStorage;
import javax.inject.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class UserServiceImpl implements UserService {
  @NonNull private final UserStorage userStorage;

  @Override
  public User create(User user) {
    return userStorage.create(user);
  }

  @Override
  public User update(User user) {
    return userStorage.update(user);
  }

  @Override
  public void delete(long id) {
    userStorage.delete(id);
  }

  @Override
  public User findById(long id) {
    return userStorage.findById(id);
  }

  @Override
  public User findByUsername(String username) {
    return userStorage.findByUsername(username);
  }
}
