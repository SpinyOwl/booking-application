package com.spinyowl.booking.application.service;

import com.spinyowl.booking.application.model.User;

public interface UserService {

  /**
   * Create user.
   *
   * @param user user to create.
   * @return created user.
   */
  User create(User user);

  /**
   * Update user.
   *
   * @param user user to update.
   * @return updated user.
   */
  User update(User user);

  /**
   * Delete user.
   *
   * @param id user id to delete.
   */
  void delete(long id);

  /**
   * Find user by id.
   *
   * @param id user id to find.
   * @return user with specified id.
   */
  User findById(long id);

  /**
   * Find user by username.
   *
   * @param username user username to find.
   * @return user with specified username.
   */
  User findByUsername(String username);
}
