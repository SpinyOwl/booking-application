package com.spinyowl.booking.application.storage.hashmap;

import com.spinyowl.booking.application.model.User;
import com.spinyowl.booking.application.storage.UserStorage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class UserStorageImpl implements UserStorage {

  private final AtomicLong counter = new AtomicLong(0);
  private final Map<Long, User> byId = new HashMap<>();
  private final Map<String, User> byUsername = new HashMap<>();

  @Override
  public User create(User user) {
    user.setId(nextId());
    checkBeforeCreate(user);
    byId.put(user.getId(), user);
    byUsername.put(user.getUsername(), user);
    return user;
  }

  @Override
  public User update(User user) {
    checkBeforeUpdate(user);
    byId.put(user.getId(), user);
    byUsername.put(user.getUsername(), user);
    return user;
  }

  private void checkBeforeUpdate(User user) {
    if (!byId.containsKey(user.getId())) {
      throw new RuntimeException("Can't find user with id " + user.getId());
    }
    if (!byUsername.containsKey(user.getUsername())) {
      throw new RuntimeException("Can't find user with username " + user.getUsername());
    }
    if (!Objects.equals(byId.get(user.getId()).getUsername(), user.getUsername())
        && byUsername.containsKey(user.getUsername())) {
      throw new RuntimeException("Can't update username.");
    }
  }

  @Override
  public void delete(long id) {
    User removedUser = byId.remove(id);
    byUsername.remove(removedUser.getUsername());
  }

  @Override
  public User findById(long id) {
    return byId.get(id);
  }

  @Override
  public User findByUsername(String username) {
    return byUsername.get(username);
  }

  public List<User> findAll() {
    return List.copyOf(byId.values());
  }

  private long nextId() {
    return counter.getAndIncrement();
  }

  private void checkBeforeCreate(User newUser) {
    if (byUsername.containsKey(newUser.getUsername()))
      throw new RuntimeException(
          "User with username " + newUser.getUsername() + " already exists.");
  }
}
