package com.spinyowl.booking.application.storage;

import com.spinyowl.booking.application.model.User;
import java.util.List;

public interface UserStorage {

  /** Create user. */
  User create(User user);

  /** Update user. */
  User update(User user);

  /** Delete user. */
  void delete(long id);

  /** Find user by id. */
  User findById(long id);

  User findByUsername(String username);

  List<User> findAll();
}
