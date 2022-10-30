package com.spinyowl.booking.application.storage.hashmap;

import com.spinyowl.booking.application.model.Table;
import com.spinyowl.booking.application.storage.TableStorage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class TableStorageImpl implements TableStorage {

  private final AtomicLong counter = new AtomicLong(0);
  private final Map<Long, Table> tables = new HashMap<>();

  @Override
  public Table create(Table table) {
    table.setId(nextId());
    tables.put(table.getId(), table);
    return table;
  }

  @Override
  public Table update(Table table) {
    tables.put(table.getId(), table);
    return table;
  }

  @Override
  public void delete(long id) {
    tables.remove(id);
  }

  @Override
  public Table findById(long id) {
    return tables.get(id);
  }

  @Override
  public List<Table> findAll() {
    return tables.values().stream().toList();
  }

  @Override
  public List<Table> findBySeats(int seats) {
    return tables.values().stream().filter(table -> table.getSeats() == seats).toList();
  }

  @Override
  public List<Table> findByMinSeats(int seats) {
    return tables.values().stream().filter(table -> table.getSeats() >= seats).toList();
  }

  private long nextId() {
    return counter.getAndIncrement();
  }
}
